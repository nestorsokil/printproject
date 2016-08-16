package com.myproject.sample.service;

import com.myproject.sample.exception.UnsuccessfulProcessingException;
import com.myproject.sample.model.Storage;
import com.myproject.sample.model.User;

import java.io.IOException;
import java.io.InputStream;

public interface StorageService {
    Storage getStorageById(String id);

    String saveProject(User uploader, InputStream fileStream, String fileName) throws UnsuccessfulProcessingException;
}
