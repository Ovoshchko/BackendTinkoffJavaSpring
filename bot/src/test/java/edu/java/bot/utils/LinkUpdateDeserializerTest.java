package edu.java.bot.utils;

import edu.java.bot.dto.request.LinkUpdate;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.kafka.common.errors.SerializationException;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LinkUpdateDeserializerTest {
    private static final LinkUpdateDeserializer DESERIALIZER = new LinkUpdateDeserializer();
    private static final String TOPIC = "test-topic";
    public static final LinkUpdate LINK_UPDATE = new LinkUpdate(1L, "http://example.com", List.of("description"), List.of(1L));

    @Test
    void deserialize_Success() {
        byte[] data =
            "{\"id\":1,\"url\":\"http://example.com\",\"description\":[\"description\"],\"tgChatIds\":[1]}"
                .getBytes(StandardCharsets.UTF_8);

        LinkUpdate deserialized = DESERIALIZER.deserialize(TOPIC, data);

        assertNotNull(deserialized);
        assertThat(deserialized).isEqualTo(LINK_UPDATE);
    }

    @Test
    void deserialize_NullData_ReturnsNull() {
        LinkUpdate deserialized = DESERIALIZER.deserialize(TOPIC, null);

        assertNull(deserialized);
    }

    @Test
    void deserialize_IOException_ThrowsSerializationException() {
        byte[] data = new byte[0];

        assertThrows(SerializationException.class, () -> DESERIALIZER.deserialize(TOPIC, data));
    }
}

