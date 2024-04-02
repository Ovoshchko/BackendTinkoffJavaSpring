package edu.java.scrapper.controller;

import com.giffing.bucket4j.spring.boot.starter.filter.servlet.ServletRequestFilter;
import edu.java.scrapper.service.chat.TgChatIdsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.cache.CacheManager;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext
public class BucketLimitControllerTest {

    public static final String TG_CHAT_URL = "/tg-chat/";
    public static final long USER_ID = 2L;
    @MockBean
    private TgChatIdsService tgChatIdsService;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private CacheManager cacheManager;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        var bean = context.getBean(ServletRequestFilter.class);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).addFilter(bean).build();
        doNothing().when(tgChatIdsService).registerUserChat(anyLong());
        cacheManager.getCache("rate-limit-buckets").clear();
    }

    @Test
    public void bucketLimitChatControllerTest() throws Exception {
        for (int i = 0; i < 5; i++) {
            mockMvc.perform(post(TG_CHAT_URL + USER_ID))
                .andExpect(status().isOk());
        }

        mockMvc.perform(post(TG_CHAT_URL + USER_ID))
            .andExpect(status().isTooManyRequests());
    }
}
