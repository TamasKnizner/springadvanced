package com.epam.tamasknizner.springadvancedtraining.domain.request.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TreeSet;

public class AirDatesSerializer extends JsonSerializer<TreeSet<LocalDateTime>> {
    @Override
    public void serialize(final TreeSet<LocalDateTime> value, final JsonGenerator gen, final SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        for(LocalDateTime date : value) {
            gen.writeString(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
        gen.writeEndArray();
    }
}
