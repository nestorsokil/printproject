package com.myproject.sample.imgprocess;

import com.myproject.sample.config.ApplicationConfigurator;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Graphics2DImageScaler implements ImageScaler{
    @Inject private ApplicationConfigurator appConfig;

    @Override
    public ImageInfo identify(File source) throws IOException{
        BufferedImage sourceImg = ImageIO.read(source);
        long size = source.length() / 1024;
        return new ImageInfo(FilenameUtils.getName(source.getName()),
                sourceImg.getWidth(),
                sourceImg.getHeight(), size);
    }

    @Override
    public void scale(File source, File target, int width, int height) throws IOException{
        BufferedImage sourceImg = ImageIO.read(source);
        BufferedImage targetImg = getResizedImg(sourceImg, width, height);
        String ext = FilenameUtils.getExtension(target.getName());

        ImageIO.write(targetImg, ext , target);

    }

    private BufferedImage getResizedImg(BufferedImage original, int width, int height){
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resized.createGraphics();
        graphics2D.drawImage(original, 0, 0, width, height, null);
        graphics2D.dispose();
        return resized;
    }


}
