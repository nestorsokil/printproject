package com.myproject.sample.rest;

import org.jboss.resteasy.annotations.providers.multipart.PartType;
import javax.ws.rs.FormParam;

public class FileUploadForm {
    private byte[] filedata;

    public FileUploadForm() {}

    public byte[] getFileData() {
        return filedata;
    }

    @FormParam("file")
    @PartType("application/octet-stream")
    public void setFileData(final byte[] filedata) {
        this.filedata = filedata;
    }
}
