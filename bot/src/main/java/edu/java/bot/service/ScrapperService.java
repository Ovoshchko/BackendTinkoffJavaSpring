package edu.java.bot.service;

import edu.java.bot.client.scrapper.ScrapperWebClient;
import edu.java.bot.dto.request.AddLinkRequest;
import edu.java.bot.dto.request.RemoveLinkRequest;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScrapperService {

    private ScrapperWebClient scrapperWebClient;

    public String registerUserChat(Long id) {
        return scrapperWebClient.registerUserChat(id);
    }

    public String deleteUserChat(Long id) {
        return scrapperWebClient.deleteUserChat(id);
    }

    public List<String> getAllLinks(Long id) {
        return scrapperWebClient.getAllLinks(id)
            .stream()
            .map(response -> response.url().toString())
            .collect(Collectors.toList());
    }

    @SneakyThrows
    public String addLink(Long id, String url) {
        return scrapperWebClient.addLink(id, new AddLinkRequest(new URI(url))).url().toString();
    }

    @SneakyThrows
    public String deleteLink(Long id, String url) {
        return scrapperWebClient.deleteLink(id, new RemoveLinkRequest(new URI(url))).url().toString();
    }
}
