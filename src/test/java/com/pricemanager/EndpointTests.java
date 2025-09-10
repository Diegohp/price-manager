package com.pricemanager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EndpointTests {

    @Autowired
    private MockMvc mockMvc;

    private final Long brandId = 1L;
    private final Long productId = 35455L;

    @Test
    @DisplayName("Test 1: 2020-06-14 10:00 - Base Rate")
    void test1_baseRate() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("date", "2020-06-14T10:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList", is(1)))
                .andExpect(jsonPath("$.price", is(35.50)))
                .andExpect(jsonPath("$.priority", is(0)))
                .andExpect(jsonPath("$.currency", is("EUR")));
    }

    @Test
    @DisplayName("Test 2: 2020-06-14 16:00 - High Priority Rate")
    void test2_highPriorityRate() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("date", "2020-06-14T16:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList", is(2)))
                .andExpect(jsonPath("$.price", is(25.45)))
                .andExpect(jsonPath("$.priority", is(1)))
                .andExpect(jsonPath("$.currency", is("EUR")));
    }

    @Test
    @DisplayName("Test 3: 2020-06-14 21:00 - Base Rate Evening")
    void test3_baseRateEvening() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("date", "2020-06-14T21:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList", is(1)))
                .andExpect(jsonPath("$.price", is(35.50)))
                .andExpect(jsonPath("$.priority", is(0)))
                .andExpect(jsonPath("$.currency", is("EUR")));
    }

    @Test
    @DisplayName("Test 4: 2020-06-15 10:00 - Mid Day Rate")
    void test4_midDayRate() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("date", "2020-06-15T10:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList", is(3)))
                .andExpect(jsonPath("$.price", is(30.50)))
                .andExpect(jsonPath("$.priority", is(1)))
                .andExpect(jsonPath("$.currency", is("EUR")));
    }

    @Test
    @DisplayName("Test 5: 2020-06-16 21:00 - Extended Rate")
    void test5_extendedRate() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("date", "2020-06-16T21:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList", is(4)))
                .andExpect(jsonPath("$.price", is(38.95)))
                .andExpect(jsonPath("$.priority", is(1)))
                .andExpect(jsonPath("$.currency", is("EUR")));
    }
}
