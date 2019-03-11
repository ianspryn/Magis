package com.magis.app.models;

public class LessonContent {
    String content;
    String type;

    public LessonContent(String content, String type) {
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
