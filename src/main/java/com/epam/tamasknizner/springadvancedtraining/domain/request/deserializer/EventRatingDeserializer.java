package com.epam.tamasknizner.springadvancedtraining.domain.request.deserializer;

import com.epam.tamasknizner.springadvancedtraining.domain.EventRating;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * @author Tamas_Knizner
 */
public class EventRatingDeserializer extends JsonDeserializer<EventRating> {
    @Override
    public EventRating deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return EventRating.valueOf(jsonParser.getText().toUpperCase());
    }
}
