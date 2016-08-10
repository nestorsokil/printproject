package com.myproject.sample.imgprocess;

public interface ImageScaler {
    String STORAGE = System.getenv("JBOSS_HOME") +
            "\\standalone\\my_uploads\\";

    String identify(String filename);

    void scale(String source, String target, int width, int height);

}
