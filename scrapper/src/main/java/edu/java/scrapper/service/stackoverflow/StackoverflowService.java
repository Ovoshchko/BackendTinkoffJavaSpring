package edu.java.scrapper.service.stackoverflow;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

public interface StackoverflowService {
    String UNKNOWN_UPDATE = "Пришло обновление, но я не знаю какое(";
    String NOT_ANSWERS_UPDATE = "Что-то произошло. Но это было не в ответах";
    String BAD_LINK = "Плохая ссылка";

    List<String> checkForUpdates(URI url, LocalDateTime time);
}
