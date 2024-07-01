package online.codevault.com.signalwire.api;

import online.codevault.com.signalwire.api.models.Fax;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestFax {

    @Test
    public void testParse() throws IOException {
        try (InputStream is = TestFax.class.getResourceAsStream("/fax.json")) {
            ObjectMapperHelper.getInstance().readValue(new InputStreamReader(is), Fax.class);
        }
    }

}
