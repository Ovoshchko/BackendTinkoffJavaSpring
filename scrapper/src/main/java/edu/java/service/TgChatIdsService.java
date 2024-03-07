package edu.java.service;

import edu.java.exception.AlreadyExistsException;
import edu.java.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TgChatIdsService {

    public static final long INVALID_ID = 666;

    public ResponseEntity registerUserChat(Long id) {
        if (id == INVALID_ID) {
            throw new AlreadyExistsException("Такой пользователь уже существует");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Чат зарегистрирован");
    }

    public ResponseEntity deleteUserChat(Long id) {
        if (id == INVALID_ID) {
            throw new NotFoundException("Такого пользователя не существует");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Чат успешно удалён");
    }
}
