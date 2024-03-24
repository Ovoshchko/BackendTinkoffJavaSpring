package edu.java.scrapper.service.github;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

public interface GitService {

    List<String> checkForUpdates(URI url, LocalDateTime time);
}
