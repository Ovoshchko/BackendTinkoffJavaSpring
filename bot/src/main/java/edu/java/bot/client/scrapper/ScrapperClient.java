package edu.java.bot.client.scrapper;

import edu.java.bot.dto.request.AddLinkRequest;
import edu.java.bot.dto.request.RemoveLinkRequest;
import edu.java.bot.dto.response.LinkResponse;
import java.util.List;

public interface ScrapperClient {

    String registerUserChat(Long id);

    String deleteUserChat(Long id);

    List<LinkResponse> getAllLinks(Long tgChatId);

    LinkResponse addLink(Long tgChatId, AddLinkRequest addLinkRequest);

    LinkResponse deleteLink(Long tgChatId, RemoveLinkRequest removeLinkRequest);
}
