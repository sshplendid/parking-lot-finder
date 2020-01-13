package me.shawn.challenge.parkinglotapi.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ResponseAdviceTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .build();
    }

    @Test
    void errorTest() throws Exception {
        // GIVEN
        String uri = "/error";

        // WHEN and THEN
        mockMvc.perform(get(uri))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }

    @Test
    void wrongPathTest() throws Exception {
        // GIVEN
        String uri = "/this/is/wrong";

        // WHEN and THEN
        mockMvc.perform(get(uri))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}