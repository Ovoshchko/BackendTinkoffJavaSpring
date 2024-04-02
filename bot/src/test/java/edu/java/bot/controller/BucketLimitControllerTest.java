package edu.java.bot.controller;

import com.giffing.bucket4j.spring.boot.starter.filter.servlet.ServletRequestFilter;
import edu.java.bot.service.UpdateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext
public class BucketLimitControllerTest {

    public static final String UPDATES_URL = "/updates";
    private static String LINK_UPDATE = """
        {
          "id": 1,
          "url": "http://example.com",
          "descriptions": ["description"],
          "categories": [1]
        }
        """;
    @MockBean
    private UpdateService updateService;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private CacheManager cacheManager;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        var bean = context.getBean(ServletRequestFilter.class);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).addFilter(bean).build();
        doNothing().when(updateService).postUpdate(any());
        cacheManager.getCache("rate-limit-buckets").clear();
    }

    @Test
    public void bucketLimitChatControllerTest() throws Exception {
        for (int i = 0; i < 5; i++) {
            mockMvc.perform(post(UPDATES_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(LINK_UPDATE))
                .andExpect(status().isOk());
        }

        mockMvc.perform(post(UPDATES_URL))
            .andExpect(status().isTooManyRequests());
    }
}
