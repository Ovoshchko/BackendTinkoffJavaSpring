package edu.java.scrapper.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "stackoverflowanswers", schema = "linkviewer")
@Data
@Accessors(chain = true)
public class StackoverflowAnswer {

    @Column(name = "name")
    private String name;

    @Id
    @Column(name = "answer_id")
    private Long answerId;

    @Column(name = "question_id")
    private Long questionId;
}
