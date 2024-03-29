package com.coldlight.spring_project.rest;

import com.coldlight.spring_project.Application;
import com.coldlight.spring_project.dto.FileDto;
import com.coldlight.spring_project.mapper.FileMapper;
import com.coldlight.spring_project.model.FileEntity;
import com.coldlight.spring_project.support.DataSourceStub;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
@ContextConfiguration(classes = Application.class)
@Import(value = DataSourceStub.class)
public class FileRestControllerV1Test {
    @Autowired
    private FileRestControllerV1 fileRestControllerV1;
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "USER")
    @Sql({"/mock_data.sql"})
    public void saveFileTest() throws Exception {
        FileDto file = new FileDto();
        file.setName("File");
        file.setFilePath("File path...");

        String contentAsString = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/files")
                .content(objectMapper.writeValueAsString(file))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn()
                .getResponse().getContentAsString();
        FileDto result = objectMapper.readValue(contentAsString, FileDto.class);

        assertEquals("File", result.getName());
        assertEquals("File path...", result.getFilePath());
    }

    @Test
    @WithMockUser(roles = "USER")
    @Sql({"/mock_data.sql"})
    public void getFileByIdTest() throws Exception{
        FileDto file = new FileDto();
        file.setName("File");
        file.setFilePath("File path");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/files")
                        .content(objectMapper.writeValueAsString(file))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/files/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("File"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.filePath").value("File path"));
    }
    @Test
    @Sql({"/mock_data.sql"})
    public void getFilesTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/files")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("FileOne"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].filePath").value("FilePathOne"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].name").value("FileTwo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].filePath").value("FilePathTwo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].name").value("FileThree"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].filePath").value("FilePathThree"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[3].name").value("FileFour"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[3].filePath").value("FilePathFour"));
    }

    @Test
    @WithMockUser(roles = "USER")
    @Sql({"/mock_data.sql"})
    public void deleteFileTest() throws Exception {
        FileDto file = new FileDto();
        file.setName("File");
        file.setFilePath("File path");

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/api/v1/files");
        mockMvc.perform(request).andReturn();
    }
}
