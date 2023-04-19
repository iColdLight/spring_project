package com.coldlight.spring_project.service;

import com.coldlight.spring_project.Application;
import com.coldlight.spring_project.model.FileEntity;
import com.coldlight.spring_project.repository.FileRepository;
import com.coldlight.spring_project.service.impl.FileServiceImpl;
import com.coldlight.spring_project.support.DataSourceStub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@ContextConfiguration(classes = Application.class)
@Import(DataSourceStub.class)
public class FileServiceImplTest {
    @Autowired
    private FileServiceImpl fileService;

    @MockBean
    private FileRepository fileRepository;

    @Captor
    ArgumentCaptor<FileEntity> fileEntityArgumentCaptor;

    @Captor
    ArgumentCaptor<Long> longValueArgumentCaptor;

    @Test
    public void createFileTest() {
        //given
        String fileName = "Text";
        String filePath = "File Path...";

        FileEntity file = new FileEntity();
        file.setName(fileName);
        file.setFilePath(filePath);

        //when
        doNothing().when(fileRepository).save(fileEntityArgumentCaptor.capture());

        //then
        fileService.save(file, 1L);
        FileEntity value = fileEntityArgumentCaptor.getValue();
        Assertions.assertEquals(fileName, value.getName());
        Assertions.assertEquals(filePath, value.getFilePath());
    }

    @Test
    public void createFileExceptionTest() {
        //given
        String fileName = "Text";
        String filePath = "File Path...";

        FileEntity file = new FileEntity();
        file.setName(fileName);
        file.setFilePath(filePath);

        //when
        when(fileRepository.save(file)).thenThrow(new RuntimeException());

        //then
        Assertions.assertThrows(RuntimeException.class, () -> {
            fileService.save(file, 1L);
        });
    }

    @Test
    public void getAllFilesTest() {
        //given
        String fileName = "Text";
        String filePath = "File Path...";

        FileEntity file = new FileEntity();
        file.setName(fileName);
        file.setFilePath(filePath);

        //given
        String fileName2 = "Picture";
        String filePath2 = "Picture File Path...";

        FileEntity file2 = new FileEntity();
        file2.setName(fileName2);
        file2.setFilePath(filePath2);

        //when
        when(fileRepository.findAll()).thenReturn(List.of(file, file2));

        //then
        List<FileEntity> allFiles = fileService.getAll();
        assertEquals(2, allFiles.size());

        FileEntity firstFile = allFiles.get(0);
        assertEquals(fileName, firstFile.getName());
        assertEquals(filePath, firstFile.getFilePath());


        FileEntity secondFile = allFiles.get(1);
        assertEquals(fileName2, secondFile.getName());
        assertEquals(filePath2, secondFile.getFilePath());
    }

    @Test
    public void getFilesEmptyTest() {
        //when
        when(fileRepository.findAll()).thenThrow(new RuntimeException());

        //then
        Assertions.assertThrows(RuntimeException.class, () -> {
            fileService.getAll();
        });
    }

    @Test
    public void getFileByIDTest() {
        //given
        String fileName = "Text";
        String filePath = "File Path...";

        FileEntity file = new FileEntity();
        file.setName(fileName);
        file.setFilePath(filePath);
        Long id = 1L;

        //when
        when(fileRepository.findById(id)).thenReturn(Optional.of(file));

        //then
        FileEntity fileByID = fileService.getById(id);
        assertEquals(fileName, fileByID.getName());
        assertEquals(filePath, fileByID.getFilePath());
    }

    @Test
    public void getFileByIDException() {
        //given
        Long id = 1L;

        //when
        when(fileRepository.findById(id)).thenThrow(new RuntimeException());

        //then
        Assertions.assertThrows(RuntimeException.class, () -> {
            fileService.getById(id);
        });
    }

    @Test
    public void deleteFileTest() {
        //given
        String fileName = "Text";
        String filePath = "File Path...";

        FileEntity file = new FileEntity();
        file.setName(fileName);
        file.setFilePath(filePath);
        Long id = 1L;

        //when
        when(fileRepository.getById(1L)).thenReturn(file);
        doNothing().when(fileRepository).deleteById(longValueArgumentCaptor.capture());


        //then
        fileService.delete(1L);
        Long value = longValueArgumentCaptor.getValue();
        Assertions.assertEquals(1L, value.longValue());
    }

    @Test
    public void deleteFileNotFoundTest() {
        //when
        doThrow(RuntimeException.class).when(fileRepository).deleteById(1L);

        //then
        Assertions.assertThrows(RuntimeException.class, () -> {
            fileService.delete(1L);
        });
    }
}
