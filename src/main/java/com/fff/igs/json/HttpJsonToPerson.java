package com.fff.igs.json;

import com.fff.igs.data.Person;
import com.fff.igs.tools.HttpTool;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Logger;

public class HttpJsonToPerson {
    private static final Logger LOGGER = Logger.getLogger(HttpJsonToPerson.class.getName());

    public Person parse(HttpServletRequest request) {
        Person person = null;
        String strBody = "";

        HttpTool httpTool = new HttpTool();
        try {
            strBody = httpTool.getBody(request);
            person = new Gson().fromJson(strBody, Person.class);
        } catch (IllegalStateException e) {
            LOGGER.warning(e.getMessage());
            LOGGER.warning("Illegal data : " + strBody);
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        }

        return person;
    }
}
