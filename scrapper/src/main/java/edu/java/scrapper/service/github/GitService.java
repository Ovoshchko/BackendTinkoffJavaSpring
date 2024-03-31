package edu.java.scrapper.service.github;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

public interface GitService {
    String UNKNOWN_UPDATE = "Пришло обновление, но я не знаю какое(";
    String NOT_COMMIT_UPDATE = "Что-то произошло. Но это было не в коммитах";
    String NEW_COMMIT = "Появился новый коммит!";
    String NEW_COMMENTS_UPDATE = "Появились новые комменты. Количество: ";
    String BAD_LINK = "Плохая ссылка";

    List<String> checkForUpdates(URI url, LocalDateTime time);
}
