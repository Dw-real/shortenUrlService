package com.example.shortenurlservice.presentation;

import com.example.shortenurlservice.application.SimpleShortenUrlService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ShortenUrlRestController.class)
class ShortenUrlRestControllerTest {

    @MockBean
    private SimpleShortenUrlService simpleShortenUrlService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("원래의 URL로 리다이렉트 되어야한다.")
    void redirectTest() throws Exception {
        String expectedOriginalUrl = "https://www.github.com/Dw-real";

        when(simpleShortenUrlService.getOriginalUrlByShortenUrlKey(any())).thenReturn(
                expectedOriginalUrl
        );

        mockMvc.perform(get("/any-key"))
                .andExpect(status().isMovedPermanently())
                .andExpect(header().string("Location", expectedOriginalUrl));

    }
}