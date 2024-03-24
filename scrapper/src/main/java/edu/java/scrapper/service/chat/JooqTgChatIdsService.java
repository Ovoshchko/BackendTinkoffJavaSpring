package edu.java.scrapper.service.chat;

import edu.java.scrapper.exception.AlreadyExistsException;
import edu.java.scrapper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.exception.IntegrityConstraintViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JooqTgChatIdsService implements TgChatIdsService {

    private final UserRepository jooqUserRepository;

    @Override
    public void registerUserChat(Long id) {
        try {
            int res = jooqUserRepository.add(id);
        } catch (IntegrityConstraintViolationException exception) {
            throw new AlreadyExistsException(ALREADY_EXISTS_RESPONSE);
        }
    }

    @Override
    public void deleteUserChat(Long id) {
        jooqUserRepository.remove(id);
    }
}
