package com.sis.scrum.model;

import com.sis.scrum.constant.FeedbackType;
import lombok.Data;

@Data
public class Feedback {
    private String name;
    private String body;
    private FeedbackType type;
}
