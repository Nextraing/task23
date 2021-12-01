package utils.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import static utils.Log.LOG;

public class JsonConverter {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static String convertObjectToJson(Object o){

        String jsonBody = "";

        try {
            jsonBody = OBJECT_MAPPER.writeValueAsString(o);
            LOG.info("JsonBody was created successfully.");

        } catch (JsonProcessingException e) {

            LOG.error("Can't create jsonBody:\n{}", e.getMessage());
        }

        return jsonBody;
    }
}
