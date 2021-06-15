package xyz.rohan;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class App implements RequestHandler<APIGatewayProxyResponseEvent, APIGatewayProxyResponseEvent> {

    /**
     * Accepts a response event and context
     * @param apiGatewayProxyResponseEvent Event
     * @param context Context
     * @return a String in the result of json format from compareFaces function
     */
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent, Context context) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("Content-Type", "application/json");
        headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token");
        headers.put("Access-Control-Allow-Methods", "POST,OPTIONS");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        String output = FaceCompare.compareFaces();
        JsonElement jsonElement = new JsonParser().parse(output);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(jsonElement);

        return response
                .withStatusCode(200)
                .withBody(json);
    }
}
