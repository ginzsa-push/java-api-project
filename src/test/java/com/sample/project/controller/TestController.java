package com.sample.project.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest
@AutoConfigureMockMvc
class ValidateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testValidateEndpoint() throws Exception {
        String requestBody = TestUtils.loadJsonFromResource("data/data.json");

        mockMvc.perform(post("/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].number", is("0ff144c2-73a6-42e6-b785-0027ec286f9b")))
                .andExpect(jsonPath("$[0].time_unit", endsWith("20250 days")))
                .andExpect(jsonPath("$[1].number", is("2ff144c2-73a6-42e6-b785-0027ec286f9b")))
                .andExpect(jsonPath("$[1].time_unit", endsWith("180 years")))
                .andExpect(jsonPath("$[2].number", is("3ff144c2-73a6-42e6-b785-0027ec286f9b")))
                .andExpect(jsonPath("$[2].time_unit", endsWith("1200 months")))
                .andExpect(jsonPath("$[3].number", is("1ff144c2-73a6-42e6-b785-0027ec286f9b")))
                .andExpect(jsonPath("$[3].time_unit", endsWith("90 years")))
                .andExpect(jsonPath("$[4].number", is("5ff144c2-73a6-42e6-b785-0027ec286f9b")))
                .andExpect(jsonPath("$[4].time_unit", endsWith("5 patatoes")));
    }
}

