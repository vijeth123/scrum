package com.sis.scrum.service;

import com.sis.scrum.model.Feedback;
import com.sis.scrum.model.Retrospective;
import com.sis.scrum.repository.RetrospectiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class RetrospectiveService {

    private final RetrospectiveRepository retrospectiveRepository;

    @Autowired
    public RetrospectiveService(RetrospectiveRepository retrospectiveRepository) {
        this.retrospectiveRepository = retrospectiveRepository;
    }

    public Retrospective save(Retrospective retrospective) {
        if (Objects.isNull(retrospective.getDate()) || CollectionUtils.isEmpty(retrospective.getParticipants())) {
            throw new IllegalArgumentException("Date and participants are mandatory");
        }
        if(!CollectionUtils.isEmpty(retrospective.getFeedbackItems())) {
            throw new IllegalArgumentException("When creating a retrospective, the feedback items should be empty.");
        }
        Retrospective savedRetrospective = retrospectiveRepository.save(retrospective);
        log.info("Successfully saved retrospective: [{}]", savedRetrospective);
        return savedRetrospective;
    }

    public Retrospective addFeedbackItem(String retrospectiveName, Feedback feedbackItem) {
        return retrospectiveRepository.addFeedbackItem(retrospectiveName, feedbackItem);
    }

    public Retrospective updateFeedbackItem(String retrospectiveName, String feedbackName, Feedback updatedFeedbackItem) {
        return retrospectiveRepository.updateFeedbackItem(retrospectiveName, feedbackName, updatedFeedbackItem);
    }

    public List<Retrospective> getAllRetrospectivesWithPagination(int currentPage, int pageSize) {
        return retrospectiveRepository.getAllRetrospectivesWithPagination(currentPage, pageSize);
    }

    public List<Retrospective> searchRetrospectivesByDate(LocalDate date) {
        return retrospectiveRepository.searchRetrospectivesByDate(date);
    }
}
