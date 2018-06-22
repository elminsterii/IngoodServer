package com.fff.igs.httpservice.activity;

import com.fff.igs.data.Activity;
import com.fff.igs.json.HttpJsonToJsonObj;
import com.fff.igs.server.ErrorHandler;
import com.fff.igs.server.ServerManager;
import com.fff.igs.server.ServerResponse;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "HttpServiceQueryActivity", value = "/queryactivity")
public class HttpServiceQueryActivity extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doPost(request, response);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        HttpJsonToJsonObj jsonToJsonObj = new HttpJsonToJsonObj();
        JsonObject jsonObj = jsonToJsonObj.parse(request);

        ServerManager serverMgr = new ServerManager();
        ErrorHandler errHandler = new ErrorHandler();

        ServerResponse serverResp = serverMgr.queryActivity(jsonObj);
        String strResponse = errHandler.handleError(serverResp.getStatus());

        if(serverResp.getStatus() == ServerResponse.STATUS_CODE.ST_CODE_SUCCESS) {
            List<Activity> lsActicities = (List<Activity>)serverResp.getContent();

            Gson gson = new GsonBuilder().setLenient().create();
            Type listType = new TypeToken<ArrayList<Activity>>() {}.getType();
            String strRes = gson.toJson(lsActicities, listType);

            JsonArray resJsonArray = new JsonArray();
            resJsonArray.add(new JsonParser().parse(strResponse));
            resJsonArray.addAll(new JsonParser().parse(strRes).getAsJsonArray());
            strResponse = resJsonArray.toString();
        }

        response.getWriter().print(strResponse);
        response.flushBuffer();
    }
}
