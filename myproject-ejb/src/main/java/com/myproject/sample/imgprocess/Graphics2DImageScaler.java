package com.myproject.sample.imgprocess;

import com.myproject.sample.config.ApplicationConfigurator;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Graphics2DImageScaler implements ImageScaler{
    @Inject private ApplicationConfigurator appConfig;

    @Override
    public String identify(String filename){
        BufferedImage sourceImg;
        File sourceFile = new File(appConfig.getTempStoragePath() + File.separator + filename);
        try {
            sourceImg = ImageIO.read(sourceFile);
        }catch (IOException ioe){
            ioe.printStackTrace();
            return "not found";
        }

        String colorModelInfo = sourceImg.getColorModel().toString();
        String res = sourceImg.getWidth() + "x" + sourceImg.getHeight();
        String size = sourceFile.length() / 1024 + "KB";
        return colorModelInfo + "\n" + res + "\n" + size;
    }

    @Override
    public void scale(String source, String target, int width, int height){
        try {
            BufferedImage sourceImg = ImageIO.read(new File(appConfig.getTempStoragePath() + File.separator + source));
            BufferedImage targetImg = getResizedImg(sourceImg, width, height);
            File targetFile = new File(appConfig.getTempStoragePath() + File.separator + target);
            String ext = target.substring(target.lastIndexOf('.') + 1, target.length());

            ImageIO.write(targetImg, ext , targetFile);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    private BufferedImage getResizedImg(BufferedImage original, int width, int height){
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resized.createGraphics();
        graphics2D.drawImage(original, 0, 0, width, height, null);
        graphics2D.dispose();
        return resized;
    }

    public static void main(String[] args) {
        ImageScaler im = new Graphics2DImageScaler();
        System.out.println(im.identify("Koala.jpg"));
        im.scale("Koala.jpg", "resized2D.jpg", 150, 150);
    }
}
