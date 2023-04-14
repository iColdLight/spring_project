package com.coldlight.spring_project.service;

import com.coldlight.spring_project.Application;
import com.coldlight.spring_project.service.impl.S3Service;
import com.coldlight.spring_project.support.DataSourceStub;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@ContextConfiguration(classes = Application.class)
@Import(DataSourceStub.class)
public class S3ServiceTest {
    @Autowired
    private S3Service s3Service;

    @Test
    public void testCreateBucket() {
        s3Service.createBucket();
    }

    @Test
    public void testListBuckets(){
        s3Service.listBuckets();
    }

    @Test
    public void testUploadFile(){
        s3Service.uploadFile();
    }
    @Test
    public void testListFiles(){
        s3Service.listFiles();
    }

    @Test
    public void downloadFile(){
        s3Service.downloadFile();
    }

    @Test
    public void deleteFile(){
        s3Service.deleteFile();
    }
}
