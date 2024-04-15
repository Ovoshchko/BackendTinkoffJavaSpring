package edu.java.bot.service;

import edu.java.bot.dto.request.LinkUpdate;
import edu.java.bot.listener.CommandListener;
import java.net.URI;
import java.util.List;

import edu.java.bot.service.update_processor.AllUpdateProcessorService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UpdateProcessorServiceTest {

    private final static LinkUpdate VALID_LINK_UPDATE = new LinkUpdate(1L,
        URI.create("https://ok.com"), List.of("ok"), List.of(1L));

    @Mock
    private CommandListener listener;
    @InjectMocks
    private AllUpdateProcessorService service;

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 23})
    void postUpdateReturns200(int n) {
        doNothing().when(listener).sendMessage(any());

        for (int i = 0; i < n; i++) {
            service.postUpdate(VALID_LINK_UPDATE);
        }

        verify(listener, times(n)).sendMessage(any());
    }
}

