package edu.java.service;

import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.request.RemoveLinkRequest;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;
import java.net.URI;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LinkService {

    @SneakyThrows
    public ResponseEntity getAllLinks(Long tgChatId) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ListLinksResponse(List.of(new LinkResponse(1L, new URI("https://github.com"))), 1));
    }

    @SneakyThrows
    public ResponseEntity addLink(Long tgChatId, AddLinkRequest addLinkRequest) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(new LinkResponse(1L, new URI("http://github.com")));
    }

    @SneakyThrows
    public ResponseEntity deleteLink(Long tgChatId, RemoveLinkRequest removeLinkRequest) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(new LinkResponse(1L, new URI("http://stackoverflow.com")));
    }
}
