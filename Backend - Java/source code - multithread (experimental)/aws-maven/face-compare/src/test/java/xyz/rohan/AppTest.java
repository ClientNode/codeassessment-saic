package xyz.rohan;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        App app = new App();
        Map<String, String> headers = new HashMap<>();
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("Content-Type", "application/json");
        headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token");
        headers.put("Access-Control-Allow-Methods", "POST,OPTIONS");
        headers.put("key", "null");
        APIGatewayProxyResponseEvent test = new APIGatewayProxyResponseEvent();
        test.withHeaders(headers);
        Instant start = Instant.now();
        APIGatewayProxyResponseEvent result = app.handleRequest(test, null);
        Instant end = Instant.now();
        Duration elaps = Duration.between(start, end);
        System.out.print(elaps.toMillis());
        assertEquals(result.getHeaders().get("Content-Type"), "application/json");
        String content = result.getBody();
        System.out.println(content);
        System.out.println(result.getHeaders());
        System.out.println(result.getStatusCode());
        assertNotNull(content);
    }
}
