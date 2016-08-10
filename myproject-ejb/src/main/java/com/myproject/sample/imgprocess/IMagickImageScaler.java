package com.myproject.sample.imgprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IMagickImageScaler implements ImageScaler{
    private static final File IM_DIR = new File("C:\\ImageMagick-7.0.2-Q16");

    @Override
    public String identify(String filename) {
        List<String> commands = Arrays.asList("identify", STORAGE + filename);
        return executeCommand(commands);
    }

    @Override
    public void scale(String source, String target, int width, int height){
        List<String> command = Arrays.asList(IM_DIR.toString() + "\\convert",
                STORAGE + source,
                "-resize", width + "x" + height + "!",
                STORAGE + target);
        executeCommand(command);
    }

    private String executeCommand(List<String> commands){
        String output = "";
        try{
            Process process = new ProcessBuilder()
                    .command(commands)
                    .directory(IM_DIR)
                    .inheritIO()
                    .start();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            output = builder.toString();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        return output;
    }

    public static void main(String[] args) {
        ImageScaler im = new IMagickImageScaler();
        System.out.println(im.identify("Koala.jpg"));
        im.scale("Koala.jpg", "resizedIM.jpg", 150, 150);
    }
}
