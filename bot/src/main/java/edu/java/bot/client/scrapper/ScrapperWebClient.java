package edu.java.bot.client.scrapper;

import edu.java.bot.dto.request.AddLinkRequest;
import edu.java.bot.dto.request.RemoveLinkRequest;
import edu.java.bot.dto.response.ApiErrorResponse;
import edu.java.bot.dto.response.LinkResponse;
import edu.java.bot.dto.response.ListLinksResponse;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

public class ScrapperWebClient implements ScrapperClient {

    public static final LinkResponse NAN_RESPONSE = new LinkResponse(0L, URI.create("Nan"));
    public static final List<LinkResponse> NAN_RESPONSE_LIST = List.of(NAN_RESPONSE);
    private final static String UPDATE_WEB_PATH = "/tg-chat/{tgChatId}";
    private final static String LINKS_WEB_PATH = "/links";
    private final static String CHATS_WEB_PARAM = "Tg-Chat-Id";
    private final WebClient webClient;

    public ScrapperWebClient(String baseUrl, ExchangeFilterFunction exchangeFilterFunction) {
        webClient = WebClient.builder().baseUrl(baseUrl).filter(exchangeFilterFunction).build();
    }

    @Override
    public String registerUserChat(Long id) {
        return webClient.post()
            .uri(UPDATE_WEB_PATH, id)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class)
            .onErrorResume(WebClientResponseException.class, e ->
                Mono.just(Objects.requireNonNull(e.getResponseBodyAs(ApiErrorResponse.class)).description()))
            .onErrorResume(Exception.class, e ->
                Mono.just(e.getMessage()))
            .block();
    }

    @Override
    public String deleteUserChat(Long id) {
        return webClient.delete()
            .uri(UPDATE_WEB_PATH, id)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class)
            .onErrorResume(WebClientResponseException.class, e ->
                Mono.just(Objects.requireNonNull(e.getResponseBodyAs(ApiErrorResponse.class)).description()))
            .block();
    }

    @Override
    public List<LinkResponse> getAllLinks(Long tgChatId) {
        return webClient.get()
            .uri(UriComponentsBuilder.fromUriString(LINKS_WEB_PATH)
                .queryParam(CHATS_WEB_PARAM, tgChatId.toString()).toUriString())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(ListLinksResponse.class)
            .onErrorReturn(new ListLinksResponse(NAN_RESPONSE_LIST, NAN_RESPONSE_LIST.size()))
            .block()
            .links();
    }

    @Override
    public LinkResponse addLink(Long tgChatId, AddLinkRequest addLinkRequest) {
        return webClient.post()
            .uri(UriComponentsBuilder.fromUriString(LINKS_WEB_PATH)
                .queryParam(CHATS_WEB_PARAM, tgChatId.toString()).toUriString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(addLinkRequest)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .block();
    }

    @Override
    public LinkResponse deleteLink(Long tgChatId, RemoveLinkRequest removeLinkRequest) {
        return webClient.method(HttpMethod.DELETE)
            .uri(UriComponentsBuilder.fromUriString(LINKS_WEB_PATH)
                .queryParam(CHATS_WEB_PARAM, tgChatId.toString()).toUriString())
            .bodyValue(removeLinkRequest)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .block();
    }
}
