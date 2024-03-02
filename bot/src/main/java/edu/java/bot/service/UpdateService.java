package edu.java.bot.service;

import edu.java.bot.dto.request.LinkUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UpdateService {

    public ResponseEntity postUpdate(LinkUpdate linkUpdate) {
        return ResponseEntity.status(HttpStatus.OK).body("Обновление обработано");
    }
}
