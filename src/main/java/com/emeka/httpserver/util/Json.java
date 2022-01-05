package com.emeka.httpserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class Json {
    private static ObjectMapper myObjectMaper = defaultObjectMapper();

    /**
     *
     * @return a new ObjectMapper object
     */
    private static ObjectMapper defaultObjectMapper (){
        ObjectMapper om = new ObjectMapper();
        // makes the parsing not crash even when one property is missing
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        return om;
    }

    public static JsonNode parse(String jsonSrc) throws IOException {
        return myObjectMaper.readTree(jsonSrc);

    }

    public static <A> A fromNode(JsonNode node, Class<A> clazz) throws JsonProcessingException {
        return myObjectMaper.treeToValue(node,clazz);
    }

    public static JsonNode toJson(Object object){
       return myObjectMaper.valueToTree(object);
    }
    public  static String stringify(JsonNode node) throws JsonProcessingException {
        return generateJson(node, false);
    }

    public  static String stringifyPretty(JsonNode node) throws JsonProcessingException {
        return generateJson(node, true);
    }
    private static String generateJson(Object object, boolean pretty) throws JsonProcessingException {
        ObjectWriter writer = myObjectMaper.writer();
        if(pretty){
            writer = writer.with(SerializationFeature.INDENT_OUTPUT);
        }
        return writer.writeValueAsString(object);
    }
}
