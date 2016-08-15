package com.myproject.sample.imgprocess;

public class ImageInfo {
    private String name;
    private int width;
    private int height;
    private long size;

    public ImageInfo(String name, int width, int height, long size) {

        this.name = name;
        this.width = width;
        this.height = height;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
