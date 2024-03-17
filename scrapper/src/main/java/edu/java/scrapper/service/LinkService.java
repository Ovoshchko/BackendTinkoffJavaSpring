package edu.java.scrapper.service;

import edu.java.scrapper.dto.request.AddLinkRequest;
import edu.java.scrapper.dto.request.RemoveLinkRequest;
import edu.java.scrapper.dto.response.LinkResponse;
import edu.java.scrapper.dto.response.ListLinksResponse;
import edu.java.scrapper.exception.NotFoundException;
import java.net.URI;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LinkService {

    public static final int INVALID_ID = 666;

    @SneakyThrows
    public ResponseEntity getAllLinks(Long tgChatId) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ListLinksResponse(List.of(new LinkResponse(1L, new URI("https://github.com"))), 1));
    }

    @SneakyThrows
    public ResponseEntity addLink(Long tgChatId, AddLinkRequest addLinkRequest) {
        if (tgChatId == INVALID_ID) {
            throw new NotFoundException("К сожалению, такого пользователя я не знаю. Зарегистрируйтесь, пожалуйстаD:");
        }
        return ResponseEntity.status(HttpStatus.OK)
            .body(new LinkResponse(1L, new URI("http://github.com")));
    }

    @SneakyThrows
    public ResponseEntity deleteLink(Long tgChatId, RemoveLinkRequest removeLinkRequest) {
        if ("https://slackoverflow.com".equals(removeLinkRequest.link())) {
            throw new NotFoundException("Сожалею, такой ссылки и так не существует");
        }
        return ResponseEntity.status(HttpStatus.OK)
            .body(new LinkResponse(1L, new URI("http://stackoverflow.com")));
    }
}
