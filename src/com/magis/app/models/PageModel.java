package com.magis.app.models;

import com.magis.app.Main;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class PageModel {

    private Node page;
    private String title;
    private ArrayList<LessonContent> lessonContent = new ArrayList<>();

    public PageModel(Node page) {
        this.page = page;

        Element pageElement = (Element) this.page;
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
                System.err.println("Lesson page title of \"" + title + "\" too long. Must be less than 24 characters. Title will be clipped.");
                return title.substring(0, 24);
            } else {
                return title;
            }
        } else {
            return null;
        }
    }
}
