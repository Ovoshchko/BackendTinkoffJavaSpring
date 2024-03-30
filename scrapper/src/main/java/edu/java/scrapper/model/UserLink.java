package edu.java.scrapper.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "userlink", schema = "linkviewer")
@Data
@Accessors(chain = true)
public class UserLink {

    @EmbeddedId
    private UserLinkId userLinkId;
}
