package edu.java.bot.utils;

import lombok.Getter;

@Getter
public enum LinkTypes {
    VALID("ссылка успешно зарегистрирована."),
    NOT_KNOWN_LINK("к сожалению, я не могу отследить эту ссылку"),
    NOT_LINK("к сожалению, ссылка не ссылка");

    private final String value;

    LinkTypes(String value) {
        this.value = value;
    }

}
