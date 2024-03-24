package edu.java.scrapper.service.stackoverflow;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

public interface StackoverflowService {
    List<String> checkForUpdates(URI url, LocalDateTime time);
}
