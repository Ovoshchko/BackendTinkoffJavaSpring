package edu.java.scrapper.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "users", schema = "linkviewer")
@Data
@Accessors(chain = true)
public class User {

    @Id
    @Column(name = "tg_id")
    private Long tgId;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

}
