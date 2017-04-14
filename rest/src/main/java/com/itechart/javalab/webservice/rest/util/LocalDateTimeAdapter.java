package com.itechart.javalab.webservice.rest.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    private static Logger logger = LogManager.getLogger(LocalDateTimeAdapter.class);

    @Override
    public LocalDateTime unmarshal(String dateString) {
        try {
            Instant instant = Instant.parse(dateString);
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        } catch (DateTimeParseException e) {
            // just ignore this field if value is incorrect
            logger.warn("For input date {}, unable to parse ", dateString, e);
        }
        return null;
    }

    @Override
    public String marshal(LocalDateTime dateTime) throws Exception {
        Instant instant = dateTime.toInstant(ZoneOffset.UTC);
        return DateTimeFormatter.ISO_INSTANT.format(instant);
    }
}