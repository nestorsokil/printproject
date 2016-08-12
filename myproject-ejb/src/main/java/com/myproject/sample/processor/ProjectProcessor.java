package com.myproject.sample.processor;

import com.myproject.sample.exception.UnsuccessfulProcessingException;
import com.myproject.sample.model.Project;

public interface ProjectProcessor {
    void process(Project project) throws UnsuccessfulProcessingException;
}
