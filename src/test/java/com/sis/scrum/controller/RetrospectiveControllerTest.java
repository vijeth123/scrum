package com.sis.scrum.controller;

import com.sis.scrum.model.Retrospective;
import com.sis.scrum.service.RetrospectiveService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RetrospectiveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RetrospectiveService retrospectiveService;

    @InjectMocks
    private RetrospectiveController retrospectiveController;

    @Test
    public void testCreateRetrospective() throws Exception {
        Retrospective retrospective = new Retrospective();
        retrospective.setName("Retrospective 1");
        retrospective.setSummary("Post release retrospective");
        retrospective.setDate(LocalDate.of(2022, 7, 27));
        retrospective.setParticipants(List.of("Viktor", "Gareth", "Mike"));

        mockMvc.perform(post("/retrospectives")
                        .contentType("application/json")
                        .content("{\"name\": \"Retrospective 1\",\"summary\": \"Post release retrospective\",\"date\": \"2022-07-27\",\"participants\": [\"Viktor\", \"Gareth\", \"Mike\"]}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateRetrospectiveWithEmptyParticipants() throws Exception {
        Retrospective retrospective = new Retrospective();
        retrospective.setName("Retrospective 1");
        retrospective.setSummary("Post release retrospective");
        retrospective.setDate(LocalDate.of(2022, 7, 27));

        mockMvc.perform(post("/retrospectives")
                        .contentType("application/json")
                        .content("{\"name\": \"Retrospective 1\",\"summary\": \"Post release retrospective\",\"date\": \"2022-07-27\"}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testCreateRetrospectiveWithEmptyDate() throws Exception {
        Retrospective retrospective = new Retrospective();
        retrospective.setName("Retrospective 1");
        retrospective.setDate(LocalDate.now());
        retrospective.setParticipants(Collections.singletonList("John Doe"));

        mockMvc.perform(post("/retrospectives")
                        .contentType("application/json")
                        .content("{\"name\": \"Retrospective 1\",\"summary\": \"Post release retrospective\",\"participants\": [\"Viktor\", \"Gareth\", \"Mike\"]}"))
                .andExpect(status().is4xxClientError());
    }


}