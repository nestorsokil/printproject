package com.myproject.sample.util;

import com.myproject.sample.exception.InvalidUploadException;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class UploadUtils {
    private UploadUtils(){
    }

    public static String getFileName(MultivaluedMap<String, String> multivaluedMap) {

        String[] contentDisposition = multivaluedMap.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                String exactFileName = name[1].trim().replaceAll("\"", "");
                return exactFileName;
            }
        }
        return "file.unknown";
    }

    public static InputPart getData(MultipartFormDataInput multipartFormDataInput)
            throws IOException, InvalidUploadException {
        Map<String, List<InputPart>> map = multipartFormDataInput.getFormDataMap();
        List<InputPart> lstInputPart = map.get("file");

        for(InputPart inputPart : lstInputPart){
            MultivaluedMap<String, String> multivaluedMap = inputPart.getHeaders();
            String fileName = getFileName(multivaluedMap);

            if(fileName != null && !fileName.equalsIgnoreCase("")){
                return inputPart;
            }
        }
        throw new InvalidUploadException();
    }
}
