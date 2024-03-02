package edu.java.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TgChatIdsService {

    public ResponseEntity registerUserChat(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body("Чат зарегистрирован");
    }

    public ResponseEntity deleteUserChat(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body("Чат успешно удалён");
    }
}
