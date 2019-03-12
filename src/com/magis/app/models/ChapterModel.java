package com.magis.app.models;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class ChapterModel {

    Node chapter;
    private String image;
    private String title;
    private String description;
    private ArrayList<PageModel> pages;

    public ChapterModel(Node chapter) {
        this.chapter = chapter;

        Element chapterElement = (Element) this.chapter;
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

    public PageModel getPages(int index) {
        return pages.get(index);
    }
}
