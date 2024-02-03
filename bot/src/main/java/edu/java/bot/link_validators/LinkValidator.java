package edu.java.bot.link_validators;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public abstract class LinkValidator implements Validational {

    private LinkValidator next;

    public LinkValidator(LinkValidator next) {
        this.next = next;
    }
}
