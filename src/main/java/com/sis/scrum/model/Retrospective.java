package com.sis.scrum.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class Retrospective {
    private String name;
    private String summary;
    private LocalDate date;
    private List<String> participants = new ArrayList<>();
    private Map<String, Feedback> feedbackItems = new HashMap<>();
}