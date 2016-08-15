package com.myproject.sample.imgprocess;

import java.io.File;
import java.io.IOException;

public interface ImageScaler {
    ImageInfo identify(File source) throws IOException;

    void scale(File source, File target, int width, int height) throws IOException;
}
