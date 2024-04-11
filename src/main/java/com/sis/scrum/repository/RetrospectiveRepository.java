package com.sis.scrum.repository;

import com.sis.scrum.model.Feedback;
import com.sis.scrum.model.Retrospective;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class RetrospectiveRepository {

    private final Map<String, Retrospective> retrospectivesMap = new HashMap<>();

    public Retrospective save(Retrospective retrospective) {
        if (retrospectivesMap.containsKey(retrospective.getName())) {
            log.error("Retrospective with the name: [{}] already present in the datastore.", retrospective.getName());
            throw new IllegalArgumentException("Retrospective with the same name is already present in the datastore.");
        }
        retrospectivesMap.put(retrospective.getName(), retrospective);
        return retrospective;
    }

    public Retrospective addFeedbackItem(String retrospectiveName, Feedback feedbackItem) {
        validateRetrospectiveName(retrospectiveName);
        Retrospective retrospective = retrospectivesMap.get(retrospectiveName);
        retrospective.getFeedbackItems().put(feedbackItem.getName(), feedbackItem);
        log.info("Successfully added feedback: [{}] to the retrospective: [{}]", feedbackItem, retrospectiveName);
        return retrospective;
    }

    public Retrospective updateFeedbackItem(String retrospectiveName, String feedbackName, Feedback updatedFeedbackItem) {
        validateRetrospectiveName(retrospectiveName);
        Retrospective retrospective = retrospectivesMap.get(retrospectiveName);
        Map<String, Feedback> feedbackItems = retrospective.getFeedbackItems();

        Feedback existingFeedback = feedbackItems.get(feedbackName);
        existingFeedback.setBody(updatedFeedbackItem.getBody());
        existingFeedback.setType(updatedFeedbackItem.getType());
        feedbackItems.put(existingFeedback.getName(), existingFeedback);
        log.info("Successfully updated the feedback: [{}] of retrospective: [{}] with a new feedback: [{}]", feedbackName, retrospectiveName, updatedFeedbackItem);
        return retrospective;
    }

    public List<Retrospective> getAllRetrospectivesWithPagination(int currentPage, int pageSize) {
        List<Retrospective> allRetrospectives = new ArrayList<>(retrospectivesMap.values());
        int startIndex = currentPage * pageSize;
        int endIndex = Math.min(startIndex + pageSize, allRetrospectives.size());
        return allRetrospectives.subList(startIndex, endIndex);
    }

    public List<Retrospective> searchRetrospectivesByDate(LocalDate date) {
        return retrospectivesMap.values().stream()
                .filter(retrospective -> retrospective.getDate().equals(date))
                .collect(Collectors.toList());
    }

    private void validateRetrospectiveName(String retrospectiveName) {
        if (!retrospectivesMap.containsKey(retrospectiveName)) {
            log.error("Retrospective with the name: [{}] does not exist in the datastore.", retrospectiveName);
            throw new IllegalArgumentException("Retrospective with the input name does not exist in the datastore.");
        }
    }
}
