package edu.java.scrapper.service.link;

import edu.java.scrapper.dto.request.AddLinkRequest;
import edu.java.scrapper.dto.request.RemoveLinkRequest;
import edu.java.scrapper.dto.response.LinkResponse;
import edu.java.scrapper.dto.response.ListLinksResponse;
import edu.java.scrapper.model.Link;

public interface LinkService {

    String USER_NOT_FOUND = "Вы еще не зарегестрированы. Пожалуйста, сделайте это в ближайшее время.";

    ListLinksResponse getAllLinks(Long tgChatId);

    LinkResponse addLink(Long tgChatId, AddLinkRequest addLinkRequest);

    LinkResponse deleteLink(Long tgChatId, RemoveLinkRequest removeLinkRequest);

    void updateLinkLastCheck(Link link);
}
