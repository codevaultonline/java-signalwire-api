package online.codevault.com.signalwire.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ObjectMapperHelper {

    private static ObjectMapper instance;

    private ObjectMapperHelper() {
    }

    public static synchronized ObjectMapper getInstance() {
        if (instance == null) {
            instance = new ObjectMapper();
        }
        instance.registerModule(new JavaTimeModule());
        return instance;
    }

}
