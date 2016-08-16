package com.myproject.sample.rest;

import org.jboss.resteasy.annotations.providers.multipart.PartType;
import javax.ws.rs.FormParam;

public class FileUploadForm {

    public FileUploadForm() {}

    private byte[] filedata;
    private String name;

    public String getName() {
        return name;
    }

    @FormParam("name")
    public void setName(String name) {
        this.name = name;
    }


    public byte[] getFileData() {
        return filedata;
    }

    @FormParam("file")
    @PartType("application/octet-stream")
    public void setFileData(final byte[] filedata) {
        this.filedata = filedata;
    }
}
