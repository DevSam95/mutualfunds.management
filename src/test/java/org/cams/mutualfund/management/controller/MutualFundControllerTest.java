package org.cams.mutualfund.management.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.time.LocalDate;
import java.util.List;

import org.cams.mutualfund.management.dto.MutualFundDto;
import org.cams.mutualfund.management.service.MutualFundService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(MutualFundController.class)
@WithMockUser(authorities = "ADMIN")
class MutualFundControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MutualFundService mutualFundService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllFunds_success() throws Exception {
        MutualFundDto dto1 = new MutualFundDto("DLSS", "TaxSaver", 10L, LocalDate.now().minusDays(1), 100);
        MutualFundDto dto2 = new MutualFundDto("ESS", "Savings", 20L, LocalDate.now(), 200);

        when(mutualFundService.getAllFunds()).thenReturn(List.of(dto1, dto2));

        mockMvc.perform(get("/api/funds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("DLSS"))
                .andExpect(jsonPath("$[1].name").value("Savings"));

        verify(mutualFundService).getAllFunds();
    }

    @Test
    void testGetMutualFundById_success() throws Exception {
        MutualFundDto dto = new MutualFundDto("DLSS", "TaxSaver", 10L, LocalDate.now().minusDays(1), 100);
        when(mutualFundService.getMutualFundById("DLSS")).thenReturn(dto);

        mockMvc.perform(get("/api/funds/DLSS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("DLSS"))
                .andExpect(jsonPath("$.name").value("TaxSaver"));

        verify(mutualFundService).getMutualFundById("DLSS");
    }

    // TODO: troubleshoot test failures below
    // @Test
    // void addNewMutualFund_success() throws Exception {
    //     MutualFundDto dto = new MutualFundDto("DLSS", "TaxSaver", 10L, LocalDate.now(), 100);

    //     mockMvc.perform(post("/api/funds")
    //                 .contentType(MediaType.APPLICATION_JSON)
    //                 .content(objectMapper.writeValueAsString(dto)))
    //             .andExpect(status().isOk());

    //     verify(mutualFundService).addNewMutualFund(any(MutualFundDto.class));
    // }

    // @Test
    // void updateMutualFundNav_success() throws Exception {
    //     mockMvc.perform(put("/api/funds/DLSS/nav/50"))
    //             .andExpect(status().isOk());

    //     verify(mutualFundService).updateMutualFund("DLSS", 50L);
    // }

    // @Test
    // void deleteMutualFund_success() throws Exception {
    //     mockMvc.perform(delete("/api/funds/DLSS"))
    //             .andExpect(status().isOk());

    //     verify(mutualFundService).deleteMutualFund("DLSS");
    // }
}
