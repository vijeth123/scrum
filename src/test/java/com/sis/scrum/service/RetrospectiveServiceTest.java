package com.sis.scrum.service;

import com.sis.scrum.model.Retrospective;
import com.sis.scrum.repository.RetrospectiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RetrospectiveServiceTest {

    @InjectMocks
    private RetrospectiveService retrospectiveService;

    @Mock
    private RetrospectiveRepository retrospectiveRepository;

    @Test
    void save() {
        Retrospective retrospective = new Retrospective();
        retrospective.setName("Retrospective 1");
        retrospective.setSummary("Post release retrospective");
        retrospective.setDate(LocalDate.of(2022, 7, 27));
        retrospective.setParticipants(List.of("Viktor", "Gareth", "Mike"));

        when(retrospectiveRepository.save(retrospective)).thenReturn(retrospective);
        Retrospective savedRetrospective = retrospectiveService.save(retrospective);
        assertEquals(retrospective, savedRetrospective);
    }

    @Test
    void saveRetrospectiveWithEmptyDate() {
        Retrospective retrospective = new Retrospective();
        retrospective.setName("Retrospective 1");
        retrospective.setSummary("Post release retrospective");
        retrospective.setParticipants(List.of("Viktor", "Gareth", "Mike"));
        assertThrows(IllegalArgumentException.class, () -> retrospectiveService.save(retrospective));
    }

    @Test
    void saveRetrospectiveWithEmptyParticipants() {
        Retrospective retrospective = new Retrospective();
        retrospective.setName("Retrospective 1");
        retrospective.setSummary("Post release retrospective");
        retrospective.setDate(LocalDate.of(2022, 7, 27));
        assertThrows(IllegalArgumentException.class, () -> retrospectiveService.save(retrospective));
    }
}