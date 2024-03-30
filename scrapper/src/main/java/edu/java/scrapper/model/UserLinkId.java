package edu.java.scrapper.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

@Embeddable
@Data
@Accessors(chain = true)
public class UserLinkId implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "tg_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "link_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Link link;
}
