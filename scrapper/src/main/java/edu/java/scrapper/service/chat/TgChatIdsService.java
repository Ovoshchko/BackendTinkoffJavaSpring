package edu.java.scrapper.service.chat;

public interface TgChatIdsService {

    String ALREADY_EXISTS_RESPONSE = "Этот пользователь уже зарегистрирован";

    void registerUserChat(Long id);

    void deleteUserChat(Long id);
}
