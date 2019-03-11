package com.magis.app.resources;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReadChapterXMLTest {

    @Test
    public void getNumPages() {
        Assert.assertEquals(8, ReadChapterXML.getNumPages(0));
    }
}