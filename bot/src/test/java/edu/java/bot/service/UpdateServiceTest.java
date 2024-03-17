package edu.java.bot.service;

import edu.java.bot.dto.request.LinkUpdate;
import edu.java.bot.listener.CommandListener;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class UpdateServiceTest {

    @Mock
    private CommandListener listener;
    @InjectMocks
    private UpdateService service;

    @Test
    void postUpdateReturns200() {
        LinkUpdate validLinkUpdate = new LinkUpdate(1L, "https://ok.com", "ok", List.of(1L));
        doNothing().when(listener).sendMessage(any());
        service.postUpdate(validLinkUpdate);
    }
}

