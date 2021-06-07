package com.codingapi.tx.datasource.relational.txc.rollback;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;

/**
 * @author jsy.
 * @title
 * @time 17/12/14.
 */

public class DiffUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(BigDecimal.class, new JsonSerializer<BigDecimal>() {
            @Override
            public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers)
                    throws IOException, JsonProcessingException {
                gen.writeString(value.setScale(2).toString());
            }
        });

        simpleModule.addSerializer(StringReader.class, new JsonSerializer<StringReader>() {
            @Override
            public void serialize(StringReader value, JsonGenerator gen, SerializerProvider serializers)
                    throws IOException, JsonProcessingException {
                gen.writeString(read(value));
            }
        });

        objectMapper.registerModule(simpleModule);
    }

    public  static boolean diff(Object oldDifDto, Object curDifDto) {
        try {
            String old = objectMapper.writeValueAsString(oldDifDto);

            String cur = objectMapper.writeValueAsString(curDifDto);
            JsonNode oldJsonNode = objectMapper.readTree(old);
            JsonNode curJsonNode = objectMapper.readTree(cur);

            if (oldJsonNode.equals(curJsonNode)) {
                return true;
            }

            return false;
        } catch (Exception e) {
            return false;
        }

    }


    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }


    public static String read(StringReader stringReader) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringReader.reset();
            int c;
            while ((c = stringReader.read()) != -1) {
                stringBuilder.append((char)c);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            return "";
        }
    }

}
