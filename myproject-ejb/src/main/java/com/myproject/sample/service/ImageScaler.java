package com.myproject.sample.service;

public interface ImageScaler {
    String STORAGE = System.getenv("JBOSS_HOME") +
            "\\standalone\\my_uploads\\";

    String identify(String filename);
}
