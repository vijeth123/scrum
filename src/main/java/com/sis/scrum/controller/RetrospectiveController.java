package com.sis.scrum.controller;

import com.sis.scrum.model.Feedback;
import com.sis.scrum.model.Retrospective;
import com.sis.scrum.service.RetrospectiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/retrospectives")
public class RetrospectiveController {

    private final RetrospectiveService service;

    @Autowired
    public RetrospectiveController(RetrospectiveService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Retrospective> create(@RequestBody Retrospective retrospective) {
        log.info("Received request to save a new retrospective: [{}]", retrospective);
        Retrospective savedRetrospective = service.save(retrospective);
        return new ResponseEntity<>(savedRetrospective, HttpStatus.CREATED);
    }


    @PostMapping("/{retrospectiveName}/feedback")
    public ResponseEntity<Retrospective> addFeedbackItem(@PathVariable String retrospectiveName, @RequestBody Feedback feedbackItem) {
        log.info("Received request to add a feedback: [{}], to the retrospective: [{}]", feedbackItem, retrospectiveName);
        Retrospective updatedRetrospective = service.addFeedbackItem(retrospectiveName, feedbackItem);
        log.info("The updated retrospective is: [{}]", updatedRetrospective);
        return ResponseEntity.ok(updatedRetrospective);
    }

    @PutMapping("/{retrospectiveName}/feedback/{feedbackName}")
    public ResponseEntity<Retrospective> updateFeedbackItem(@PathVariable String retrospectiveName, @PathVariable String feedbackName, @RequestBody Feedback updatedFeedbackItem) {
        log.info("Request received to update a feedback: [{}] of retrospective: [{}] with a new feedback: [{}]", feedbackName, retrospectiveName, updatedFeedbackItem);
        Retrospective updatedRetrospective = service.updateFeedbackItem(retrospectiveName, feedbackName, updatedFeedbackItem);
        log.info("The updated retrospective is: [{}]", updatedRetrospective);
        return ResponseEntity.ok(updatedRetrospective);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Retrospective>> getAllRetrospectivesWithPagination(@RequestParam(defaultValue = "0") int currentPage,
                                                                                  @RequestParam(defaultValue = "10") int pageSize) {
        log.info("Request received to get all the retrospectives with pagination, based on the parameters: current-page: [{}], page-size: [{}]", currentPage, pageSize);
        List<Retrospective> retrospectives = service.getAllRetrospectivesWithPagination(currentPage, pageSize);
        return ResponseEntity.ok(retrospectives);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Retrospective>> searchRetrospectivesByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Retrospective> retrospectives = service.searchRetrospectivesByDate(date);
        log.info("Search results: [{}] for the date: [{}]", retrospectives, date);
        return ResponseEntity.ok(retrospectives);
    }

}