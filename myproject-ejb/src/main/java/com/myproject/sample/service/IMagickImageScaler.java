package com.myproject.sample.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class IMagickImageScaler implements ImageScaler {
    public String identify(String filename) {
        String result = "not found";
        ProcessBuilder processBuilder = new ProcessBuilder("CMD", "/C", "magick.exe", "identify", STORAGE + filename);
        try {
            processBuilder.directory(new File("C:\\ImageMagick-7.0.2-Q16"));
            Process process = processBuilder.inheritIO().start();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            result = builder.toString();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return result;
    }

    public static void main(String[] args) {
        ImageScaler im = new IMagickImageScaler();
        System.out.println(im.identify("Koala.jpg"));
    }

}
