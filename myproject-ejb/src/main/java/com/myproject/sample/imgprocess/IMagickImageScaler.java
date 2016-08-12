package com.myproject.sample.imgprocess;

import com.myproject.sample.config.ApplicationConfigurator;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@Named(ApplicationConfigurator.IMAGICK_SCALER_BEAN_NAME)
public class IMagickImageScaler implements ImageScaler{

    /*//for debug:
    private static final File IM_DIR = new File("C:\\ImageMagick-7.0.2-Q16");*/

    @Inject private ApplicationConfigurator appConfig;
    private File IM_DIR;

    @PostConstruct
    private void init(){
        IM_DIR = new File(appConfig.getImageMagickHome());
    }

    @Override
    public String identify(String filename) {
        List<String> commands = Arrays.asList("identify", filename);
        return executeCommand(commands);
    }

    @Override
    public void scale(String source, String target, int width, int height){
        List<String> command = Arrays.asList(IM_DIR.toString() + "\\convert",
                source,
                "-resize", width + "x" + height + "!",
                target);
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
