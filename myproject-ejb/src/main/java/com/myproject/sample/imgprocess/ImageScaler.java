package com.myproject.sample.imgprocess;

public interface ImageScaler {
    String identify(String filename);

    void scale(String source, String target, int width, int height);
}
