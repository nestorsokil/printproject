package com.myproject.sample.util;

import com.myproject.sample.config.ApplicationConfigurator;
import com.myproject.sample.model.Project;
import org.apache.commons.io.FilenameUtils;

import javax.inject.Inject;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ProjectFileUtils {
    @Inject private ApplicationConfigurator appConfig;

    private ProjectFileUtils(){}

    public static String makeTempImgName(String sourceImgName, int width, int height){
        String ext = FilenameUtils.getExtension(sourceImgName);
        String baseName = FilenameUtils.getBaseName(sourceImgName);
        return baseName + width + "x" + height + "." + ext;
    }

    public static void unzipProject(InputStream fileStream, String projectPath) throws IOException{
        try (ZipInputStream zipIn = new ZipInputStream(fileStream)){

            ZipEntry entry = zipIn.getNextEntry();
            while (entry != null) {
                String filePath = projectPath + File.separator + entry.getName();
                extractFile(zipIn, filePath);
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        }
    }

    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException{
        try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            byte[] bytesIn = new byte[4096];
            int read;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }
}
