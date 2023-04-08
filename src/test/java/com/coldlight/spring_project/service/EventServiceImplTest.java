package com.coldlight.spring_project.service;

import com.coldlight.spring_project.Application;
import com.coldlight.spring_project.support.DataSourceStub;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@ContextConfiguration(classes = Application.class)
@Import(DataSourceStub.class)
public class EventServiceImplTest {
}
