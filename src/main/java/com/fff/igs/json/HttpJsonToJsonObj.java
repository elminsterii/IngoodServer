package com.fff.igs.json;

import com.fff.igs.tools.HttpTool;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Logger;

public class HttpJsonToJsonObj {
    private static final Logger LOGGER = Logger.getLogger(HttpJsonToJsonObj.class.getName());

    public JsonObject parse(HttpServletRequest request) {
        JsonObject jsonObj;
        JsonParser parser = new JsonParser();

        String strBody = "";
        HttpTool httpTool = new HttpTool();
        try {
            strBody = httpTool.getBody(request);
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        }

        jsonObj = parser.parse(strBody).getAsJsonObject();
        return jsonObj;
    }
}
