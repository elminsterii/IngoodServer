package com.fff.igs.database;

class DBConstants {

    //Person constants
    static final String TABLE_NAME_PERSON = "persons";
    static final String PERSON_COL_ID = "id";
    static final String PERSON_COL_TS = "ts";
    static final String PERSON_COL_EMAIL = "email";
    static final String PERSON_COL_USERPASSWORD = "userpassword";
    static final String PERSON_COL_DISPLAYNAME = "displayname";
    static final String PERSON_COL_AGE = "age";
    static final String PERSON_COL_GENDER = "gender";
    static final String PERSON_COL_INTERESTS = "interests";
    static final String PERSON_COL_DESCRIPTION = "description";
    static final String PERSON_COL_LOCATION = "location";
    static final String PERSON_COL_SAVEACTIVITIES = "saveactivities";
    static final String PERSON_COL_GOOD = "good";
    static final String PERSON_COL_NOGOOD = "nogood";
    static final String PERSON_COL_ONLINE = "online";
    static final String PERSON_COL_ANONYMOUS = "anonymous";

    //Activity constants
    static final String TABLE_NAME_ACTIVITY = "activities";
    static final String ACTIVITY_COL_ID = "id";
    static final String ACTIVITY_COL_TS = "ts";
    static final String ACTIVITY_COL_PUBLISHEREMAIL = "publisheremail";
    static final String ACTIVITY_COL_PUBLISHBEGIN = "publishbegin";
    static final String ACTIVITY_COL_PUBLISHEND = "publishend";
    static final String ACTIVITY_COL_LARGEACTIVITY = "largeactivity";
    static final String ACTIVITY_COL_EARLYBIRD = "earlybird";
    static final String ACTIVITY_COL_DISPLAYNAME = "displayname";
    static final String ACTIVITY_COL_DATEBEGIN = "datebegin";
    static final String ACTIVITY_COL_DATEEND = "dateend";
    static final String ACTIVITY_COL_LOCATION = "location";
    static final String ACTIVITY_COL_STATUS = "status";
    static final String ACTIVITY_COL_DESCRIPTION = "description";
    static final String ACTIVITY_COL_TAGS = "tags";
    static final String ACTIVITY_COL_GOOD = "good";
    static final String ACTIVITY_COL_NOGOOD = "nogood";
    static final String ACTIVITY_COL_ATTENTION = "attention";
    static final String ACTIVITY_COL_ATTENDEES = "attendees";
    static final String ACTIVITY_COL_MAX_ATTENTION = "maxattention";

    //Comment constants
    static final String TABLE_NAME_COMMENT = "comments";
    static final String COMMENT_COL_ID = "id";
    static final String COMMENT_COL_TS = "ts";
    static final String COMMENT_COL_PUBLISHEREMAIL = "publisheremail";
    static final String COMMENT_COL_DISPLAYNAME = "displayname";
    static final String COMMENT_COL_ACTIVITYID = "activityid";
    static final String COMMENT_COL_CONTENT = "content";

    //VerifyEmail constants
    static final String TABLE_NAME_VERIFYEMAIL = "verifyemails";
    static final String VERIFYEMAIL_COL_EMAIL = "email";
    static final String VERIFYEMAIL_COL_CODE = "code";

    //TempPassword constants
    static final String TABLE_NAME_TEMPPASSWORD = "temppassword";
    static final String TEMPPASSWORD_COL_EMAIL = "email";
    static final String TEMPPASSWORD_COL_TEMPPASSWORD = "temppassword";
}
