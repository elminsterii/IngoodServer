package com.fff.igs.server;

import java.util.HashMap;

public class GlobalProperty {
    //admins
    public static final HashMap<String, String> MAP_ADMIN_ACCOUNTS_AND_PW = new HashMap<>();
    private static final String ADMIN_ACCOUNT_01 = "fivefourfiveit@gmail.com";
    private static final String ADMIN_ACCOUNT_PW_01 = "545450779031";

    public static final String VERIFY_CODE_FOR_GOOGLE_SIGN = "google545450779031";
    public static final String VERIFY_CODE_FOR_FACEBOOK_SIGN = "facebook507790315454";

    public static void initialize() {
        MAP_ADMIN_ACCOUNTS_AND_PW.put(ADMIN_ACCOUNT_01, ADMIN_ACCOUNT_PW_01);
    }
}
