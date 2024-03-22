package edu.java.scrapper.service.chat;

import edu.java.scrapper.exception.AlreadyExistsException;
import edu.java.scrapper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcTgChatIdsService implements TgChatIdsService {

    private final UserRepository jdbcUserRepository;

    public void registerUserChat(Long id) {
        try {
            jdbcUserRepository.add(id);
        } catch (DuplicateKeyException exception) {
            throw new AlreadyExistsException(ALREADY_EXISTS_RESPONSE);
        }
    }

    public void deleteUserChat(Long id) {
        jdbcUserRepository.remove(id);
    }
}
