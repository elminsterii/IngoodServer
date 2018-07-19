package com.fff.igs.httpservice.person;

import com.fff.igs.data.Person;
import com.fff.igs.database.DatabaseManager;
import com.fff.igs.server.ErrorHandler;
import com.fff.igs.server.ServerManager;
import com.fff.igs.server.ServerResponse;
import com.fff.igs.tools.HttpTool;
import com.fff.igs.tools.StringTool;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "HttpServiceAccessPersonIcon", urlPatterns = {"/accesspersonicon/*"})
public class HttpServiceAccessPersonIcon extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpTool httpTool = new HttpTool();
        String strOwnerName = httpTool.getOwnerName(request);
        String strFileName = httpTool.getFileName(request);

        StringTool stringTool = new StringTool();
        ServerManager serverMgr = new ServerManager();
        ErrorHandler errHandler = new ErrorHandler();

        String strResponse;

        strOwnerName = transIdToEmail(strOwnerName);

        if(stringTool.checkStringNotNull(strFileName)) {
            //download icon...
            response.setContentType("image/jpg");
            ServerResponse serverResp = serverMgr.downloadPersonIcon(strOwnerName, strFileName, response.getOutputStream());

            if(serverResp.getStatus() != ServerResponse.STATUS_CODE.ST_CODE_SUCCESS) {
                strResponse = errHandler.handleError(serverResp.getStatus());
                response.getOutputStream().print(strResponse);
                response.flushBuffer();
            }
        } else {
            //take icon list...
            response.setContentType("application/json");

            ServerResponse serverResp = serverMgr.listPersonIcons(strOwnerName);
            strResponse = errHandler.handleError(serverResp.getStatus());

            if(serverResp.getStatus() == ServerResponse.STATUS_CODE.ST_CODE_SUCCESS) {
                final String TAG_ICONS = "icons";
                String strIcons = (String)serverResp.getContent();

                JsonObject jsonIcons = new JsonObject();
                jsonIcons.addProperty(TAG_ICONS, strIcons);

                JsonArray resJsonArray = new JsonArray();
                resJsonArray.add(new JsonParser().parse(strResponse));
                resJsonArray.add(jsonIcons);

                strResponse = resJsonArray.toString();
            }

            response.getOutputStream().print(strResponse);
            response.flushBuffer();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        HttpTool httpTool = new HttpTool();
        String strOwnerName = httpTool.getOwnerName(request);
        String strFileName = httpTool.getFileName(request);

        strOwnerName = transIdToEmail(strOwnerName);
        String strFullName = strOwnerName + "/" + strFileName;

        ServerManager serverMgr = new ServerManager();
        ErrorHandler errHandler = new ErrorHandler();

        ServerResponse serverResp = serverMgr.uploadPersonIcon(strOwnerName, strFullName, request.getInputStream());
        String strResponse = errHandler.handleError(serverResp.getStatus());

        response.getWriter().print(strResponse);
        response.flushBuffer();
    }

    private String transIdToEmail(String strOwnerId) {
        String strOwnerEmail = strOwnerId;

        if (strOwnerId.matches("[0-9]+")) {
            DatabaseManager dbMgr = new DatabaseManager();
            Person person = dbMgr.queryPerson(strOwnerId, null);
            if(person != null) {
                strOwnerEmail = person.getEmail();
            }
        }
        return strOwnerEmail;
    }
}
