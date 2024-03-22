package edu.java.bot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.bot.dto.request.LinkUpdate;
import edu.java.bot.service.UpdateService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UpdateControllerTest {

    private final static LinkUpdate LINK_UPDATE = new LinkUpdate(1L, "https://github.com", List.of("desc"), List.of(1L, 2L));

    @Mock
    private UpdateService updateService;
    @InjectMocks
    private UpdateController updateController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(updateController).build();
    }

    @Test
    void postUpdateReturns200() throws Exception {

        doNothing().when(updateService).postUpdate(any(LinkUpdate.class));

        mockMvc.perform(post("/updates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(LINK_UPDATE)))
            .andExpect(status().isOk());
    }

    @Test
    void postUpdateReturns400() throws Exception {

        mockMvc.perform(post("/updates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new LinkUpdate(
                    null,
                    LINK_UPDATE.url(),
                    LINK_UPDATE.description(),
                    LINK_UPDATE.tgChatIds()
                ))))
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

