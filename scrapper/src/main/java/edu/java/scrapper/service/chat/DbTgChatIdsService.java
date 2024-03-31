package edu.java.scrapper.service.chat;

import edu.java.scrapper.exception.AlreadyExistsException;
import edu.java.scrapper.repository.UserRepository;
import lombok.Data;
import org.jooq.exception.IntegrityConstraintViolationException;

@Data
public class DbTgChatIdsService implements TgChatIdsService {

    private final UserRepository userRepository;

    @Override
    public void registerUserChat(Long id) {
        try {
            userRepository.add(id);
        } catch (IntegrityConstraintViolationException exception) {
            throw new AlreadyExistsException(ALREADY_EXISTS_RESPONSE);
        }
    }

    @Override
    public void deleteUserChat(Long id) {
        userRepository.remove(id);
    }
}
