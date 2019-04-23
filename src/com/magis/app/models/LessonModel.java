package com.magis.app.models;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;

public class LessonModel {

    private Document document;
    private ArrayList<ChapterModel> chapters;

    public LessonModel() {
        this.chapters = new ArrayList<>();

        InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream("com/magis/app/resources/chapters.xml");
//        File file = new File("src/com/magis/app/resources/chapters.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        try {
            this.document = dBuilder.parse(file);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        this.document.getDocumentElement().normalize();
        NodeList chapters = this.document.getElementsByTagName("chapter");
        for (int i = 0; i < chapters.getLength(); i++) {
            Node chapter = chapters.item(i);
            ChapterModel chapterModel = new ChapterModel(chapter);
            this.chapters.add(chapterModel);
        }
    }
    public int getNumChapters() {
        return chapters.size();
    }

    public ArrayList<ChapterModel> getChapters() {
        return chapters;
    }

    public ChapterModel getChapter(int index) {
        return chapters.get(index);
    }

    public class ChapterModel {
        private String image;
        private String title;
        private String description;
        private ArrayList<PageModel> pages;
        ChapterModel(Node chapter) {

            Element chapterElement = (Element) chapter;
            this.image = chapterElement.getElementsByTagName("image").item(0).getTextContent();
            this.title = chapterElement.getElementsByTagName("title").item(0).getTextContent();
            this.description = chapterElement.getElementsByTagName("description").item(0).getTextContent();
            this.pages = new ArrayList<>();

            Element pagesElement = (Element) chapterElement.getElementsByTagName("pages").item(0);
            NodeList pages = pagesElement.getElementsByTagName("page");
            for (int i = 0; i < pages.getLength(); i++) {
                Node page = pages.item(i);
                PageModel pageModel = new PageModel(page);
                this.pages.add(pageModel);
            }
        }

        public String getImage() {
            return image;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public ArrayList<PageModel> getPages() {
            return pages;
        }

        public int getNumPages() {
            return pages.size();
        }

        public PageModel getPage(int index) {
            return pages.get(index);
        }

        public class PageModel {
            private String title;
            private ArrayList<LessonContent> lessonContent = new ArrayList<>();

            PageModel(Node page) {
                Element pageElement = (Element) page;
                NodeList contents = pageElement.getChildNodes();
                for (int i = 0; i < contents.getLength(); i++) {
                    Node contentNode = contents.item(i);
                    if (contentNode.getNodeType() == Node.ELEMENT_NODE) {
                        String content = contentNode.getTextContent();
                        String type = contentNode.getNodeName();
                        lessonContent.add(new LessonContent(content, type));
                    }
                }
                if (pageElement.hasAttributes()) {
                    this.title = pageElement.getAttributes().getNamedItem("title").getNodeValue();
                } else {
                    this.title = null;
                }
            }

            public ArrayList<LessonContent> getLessonContent() {
                return lessonContent;
            }

            public String getTitle() {
                if (title != null) {
                    if (title.length() > 24) {
                        System.err.println("Lesson page title of \"" + title + "\" too long. Must be less than 25 characters. Title will be clipped.");
                    }
                    return title;
                } else {
                    return null;
                }
            }

            public class LessonContent {
                String content;
                String type;

                LessonContent(String content, String type) {
                    this.content = content;
                    this.type = type;
                }

                public String getContent() {
                    return content;
                }

                public String getType() {
                    return type;
                }
            }
        }
    }
}
