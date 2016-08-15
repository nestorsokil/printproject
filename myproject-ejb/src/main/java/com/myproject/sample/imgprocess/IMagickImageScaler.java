package com.myproject.sample.imgprocess;

import com.myproject.sample.config.AppProperty;
import com.myproject.sample.config.ApplicationConfigurator;
import com.myproject.sample.config.ApplicationConfiguratorImpl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class IMagickImageScaler implements ImageScaler{

    /*//for debug:
    private static final File IM_DIR = new File("C:\\ImageMagick-7.0.2-Q16");*/

    private ApplicationConfigurator appConfig;

    private File IM_DIR;

    @Inject
    public IMagickImageScaler(ApplicationConfigurator appConfig){
        this.appConfig = appConfig;
        IM_DIR = new File(appConfig.getProperty(AppProperty.IM_HOME));
    }
    @Override
    public ImageInfo identify(File source) throws IOException {
        List<String> commands = Arrays.asList("identify", source.getAbsolutePath());
        String res = executeCommand(commands);
        String[] output = res.split(" ");
        String name = output[0];
        String[] resolution = output[2].replaceAll("[^0-9]", " ").split(" ");
        int width = Integer.parseInt(resolution[0]);
        int height = Integer.parseInt(resolution[1]);
        long size = Long.parseLong(resolution[6].replaceAll("[^0-9]", ""));
        return new ImageInfo(name, width, height, size);

    }

    @Override
    public void scale(File source, File target, int width, int height)throws IOException{
        List<String> command = Arrays.asList(
                IM_DIR + "\\convert",
                source.getAbsolutePath(),
                "-resize", width + "x" + height + "!",
                target.getAbsolutePath());
        executeCommand(command);
    }

    private String executeCommand(List<String> commands) throws IOException{
        String output = "";
        try{
            Process process = new ProcessBuilder()
                    .command(commands)
                    .directory(IM_DIR)
                    .inheritIO()
                    .start();

            process.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            output = builder.toString();
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
        return output;
    }
}
