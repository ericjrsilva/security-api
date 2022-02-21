import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.data.PasswordData;
import org.junit.Assert;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Result;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;
import static play.test.Helpers.contentAsString;

public class PasswordControllerIntegrationTest {

    private final List<String> RIGHT_PASSWORD_POOL = Arrays.asList("AbTp9!fok", "1A2b3C4d#");
    private final List<String> WRONG_PASSWORD_POOL = Arrays.asList("","aa","ab", "AAAbbbCc", "AbTp9!foo", "AbTp9!foA", "AbTp9 fok", "1A2B3c4d#!@$%^&*(}");

    @Test
    public void testIsValid() {
        running(fakeApplication(), () -> {
            try {
                for (String password : RIGHT_PASSWORD_POOL) {
                    JsonNode response = runRequest(new PasswordData(password));
                    Assert.assertEquals(200, response.get("status").asInt());
                    Assert.assertEquals("OK", response.get("message").asText());
                    Assert.assertEquals("true", response.get("data").asText());
                }

                for (String password : WRONG_PASSWORD_POOL) {
                    JsonNode response = runRequest(new PasswordData(password));
                    Assert.assertEquals(200, response.get("status").asInt());
                    Assert.assertEquals("OK", response.get("message").asText());
                    Assert.assertEquals("false", response.get("data").asText());
                }


                JsonNode response = runRequest(new PasswordData(""));
                Assert.assertEquals(200, response.get("status").asInt());
                Assert.assertEquals("OK", response.get("message").asText());
                Assert.assertEquals("false", response.get("data").asText());

                response = runRequest(new PasswordData(null));
                Assert.assertEquals(400, response.get("status").asInt());
                Assert.assertEquals("Password is required.", response.get("message").asText());
                Assert.assertEquals("null", response.get("data").toString());


            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

    private JsonNode runRequest(PasswordData passwordData) throws IOException {
        Result result = route(
                fakeRequest(POST, "/api/security/password/valid")
                        .header("Content-type", "application/json")
                        .bodyJson(Json.toJson(passwordData))
        );
        assertEquals(200, result.status());

        return new ObjectMapper().readTree(contentAsString(result));
    }
}
