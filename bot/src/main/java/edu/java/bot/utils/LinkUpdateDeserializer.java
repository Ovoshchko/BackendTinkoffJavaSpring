package edu.java.bot.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.bot.dto.request.LinkUpdate;
import java.io.IOException;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

@Data
@NoArgsConstructor
public class LinkUpdateDeserializer implements Deserializer<LinkUpdate> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public LinkUpdate deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            return objectMapper.readValue(data, LinkUpdate.class);
        } catch (IOException e) {
            throw new SerializationException("Error deserializing data", e);
        }
    }

    @Override
    public void close() {
    }

}
