package com.fff.igs.maintainer;

import com.fff.igs.data.Activity;
import com.fff.igs.tools.StringTool;
import com.fff.igs.tools.TimeHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ActivityMaintainer {
    private static final long ONE_DAY_MS = 24 * 60 * 60 * 1000;

    public boolean maintain(Activity activity) {
        boolean bRes;

        if(activity == null)
            return false;

        bRes = maintainStatus(activity);

        return bRes;
    }

    private boolean maintainStatus(Activity activity) {
        boolean bRes;

        String strDateBegin = activity.getDateBegin();
        String strDateEnd = activity.getDateEnd();

        StringTool stringTool = new StringTool();

        if (!stringTool.checkStringNotNull(strDateBegin)
                || !stringTool.checkStringNotNull(strDateEnd))
            return false;

        try {
            String strPattern = "yyyy-MM-dd HH:mm:ss";
            DateFormat dateFormat = new SimpleDateFormat(strPattern, Locale.getDefault());

            String strNow = TimeHelper.getCurTime();
            Date dateNow = dateFormat.parse(strNow);
            Date dateBegin = dateFormat.parse(strDateBegin);
            Date dateEnd = dateFormat.parse(strDateEnd);

            if(dateNow.after(dateEnd)) {
                activity.setStatus(Activity.ACTIVITY_STATUS.ST_ACTIVITY_DONE.ordinal());
                activity.setEarlyBird(0);
            } else if(dateNow.after(dateBegin) && dateNow.before(dateEnd)) {
                activity.setStatus(Activity.ACTIVITY_STATUS.ST_ACTIVITY_STARTING.ordinal());
                activity.setEarlyBird(0);
            } else if((dateBegin.getTime() - dateNow.getTime()) < ONE_DAY_MS) {
                activity.setStatus(Activity.ACTIVITY_STATUS.ST_ACTIVITY_READY_START.ordinal());
                activity.setEarlyBird(0);
            } else {
                activity.setStatus(Activity.ACTIVITY_STATUS.ST_ACTIVITY_NOT_START.ordinal());
                activity.setEarlyBird(1);
            }
            bRes = true;
        } catch (IllegalArgumentException | ParseException e) {
            e.printStackTrace();
            bRes = false;
        }

        return bRes;
    }
}
