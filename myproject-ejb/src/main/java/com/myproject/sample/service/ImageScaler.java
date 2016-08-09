package com.myproject.sample.service;

public interface ImageScaler {
    String STORAGE = System.getenv("JBOSS_HOME") +
            "\\standalone\\my_uploads\\";

    String identify(String filename);

    void scale(String filename, long width, long height);

    void scale(String filename, long percentage);
}
