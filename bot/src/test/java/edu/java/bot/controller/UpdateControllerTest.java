package edu.java.bot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.bot.dto.request.LinkUpdate;
import edu.java.bot.service.UpdateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UpdateControllerTest {

    private final static UpdateService UPDATE_SERVICE = mock(UpdateService.class);
    private final static UpdateController UPDATE_CONTROLLER = new UpdateController(UPDATE_SERVICE);
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(UPDATE_CONTROLLER).build();
    }

    @Test
    void postUpdateReturns200() throws Exception {
        LinkUpdate linkUpdate = new LinkUpdate(1L, "https://github.com", "desc", List.of(1L, 2L));

        when(UPDATE_SERVICE.postUpdate(any(LinkUpdate.class))).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(post("/updates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(linkUpdate)))
            .andExpect(status().isOk());
    }

    @Test
    void postUpdateReturns400() throws Exception {
        LinkUpdate linkUpdate = new LinkUpdate(null, "https://github.com", "desc", List.of(1L, 2L));

        when(UPDATE_SERVICE.postUpdate(any(LinkUpdate.class))).thenReturn(ResponseEntity.badRequest().build());

        mockMvc.perform(post("/updates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(linkUpdate)))
            .andExpect(status().isBadRequest());
    }

    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

