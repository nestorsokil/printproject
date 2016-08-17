package com.myproject.sample.service;

import com.myproject.sample.dao.StorageDao;
import com.myproject.sample.model.Storage;
import javax.inject.Inject;


public class StorageServiceImpl implements StorageService{

    @Inject private StorageDao storageDao;

    @Override
    public Storage getStorageById(String id){
        return storageDao.findById(id);
    }
}
