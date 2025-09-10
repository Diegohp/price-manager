package com.pricemanager.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pricemanager.PriceManagerApplication;
import com.pricemanager.application.dto.PriceDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = PriceManagerApplication.class)
@AutoConfigureMockMvc
class PriceManagerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "/prices";
    }

    @Test
    void shouldReturnPriceWhenValidRequest() throws Exception {
        String productId = "35455";
        String brandId = "1";
        String date = "2020-06-14T10:00:00";

        String jsonResponse = mockMvc.perform(get(baseUrl)
                        .param("productId", productId)
                        .param("brandId", brandId)
                        .param("date", date)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PriceDto response = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });

        assertThat(response.getProductId()).isEqualTo(Long.valueOf(productId));
        assertThat(response.getBrandId()).isEqualTo(Long.valueOf(brandId));
        assertThat(response.getPrice()).isInstanceOf(BigDecimal.class);
        assertThat(response.getStartDate()).isBefore(LocalDateTime.parse(date));
        assertThat(response.getEndDate()).isAfter(LocalDateTime.parse(date));
    }

    @Test
    void shouldReturnBadRequestForInvalidDateFormat() throws Exception {
        mockMvc.perform(get(baseUrl)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "invalid-date"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void shouldReturnNotFoundWhenNoPriceMatch() throws Exception {
        mockMvc.perform(get(baseUrl)
                        .param("productId", "99999") // non-existing product
                        .param("brandId", "1")
                        .param("date", "2020-06-14T10:00:00"))
                .andExpect(status().isNotFound());
    }
}
