package com.fff.igs.json;

import com.fff.igs.data.Activity;
import com.fff.igs.tools.HttpTool;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Logger;

public class HttpJsonToActivity {
    private static final Logger LOGGER = Logger.getLogger(HttpJsonToActivity.class.getName());

    public Activity parse(HttpServletRequest request) {
        Activity activity = null;
        String strBody = "";

        HttpTool httpTool = new HttpTool();
        try {
            strBody = httpTool.getBody(request);
            activity = new Gson().fromJson(strBody, Activity.class);
        } catch (IllegalStateException e) {
            LOGGER.warning(e.getMessage());
            LOGGER.warning("Illegal data : " + strBody);
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        }

        return activity;
    }
}
