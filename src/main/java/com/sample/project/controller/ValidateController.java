package com.sample.project.controller;

import com.sample.project.model.ValidationResult;
import com.sample.project.service.ValidationService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/validate")
public class ValidateController {

    @Autowired
    private ValidationService validationService;

    @PostMapping
    public List<ValidationResult> validate(@RequestBody List<Map<String, String>> payload) {
        // You can process 'payload' here to build your real validation logic
        return validationService.validate(payload);
    }
}

