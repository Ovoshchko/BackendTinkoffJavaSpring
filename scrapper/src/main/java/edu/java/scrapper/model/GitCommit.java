package edu.java.scrapper.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "gitcommits", schema = "linkviewer")
@Data
@Accessors(chain = true)
public class GitCommit {

    @Column(name = "name")
    private String name;

    @Column(name = "made_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime madeDate;

    @Id
    @Column(name = "url")
    private String url;

    @Column(name = "comment_number")
    private Long commentNumber;
}
