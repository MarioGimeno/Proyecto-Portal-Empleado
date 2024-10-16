package com.example.proyecto_portal_empleado.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
public class CustomLocalTimeDeserializer extends JsonDeserializer<LocalTime> {
    private static final DateTimeFormatter TIME_FORMATTER_HHMMSS = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter TIME_FORMATTER_HHMM = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String timeString = p.getText().trim();
        try {
            if (timeString.length() == 5) {
                // Formato HH:mm
                return LocalTime.parse(timeString, TIME_FORMATTER_HHMM);
            } else {
                // Formato HH:mm:ss
                return LocalTime.parse(timeString, TIME_FORMATTER_HHMMSS);
            }
        } catch (DateTimeParseException e) {
            throw new JsonParseException(p, "Unable to parse time: " + timeString, e);
        }
    }
}

