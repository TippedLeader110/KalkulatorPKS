package com.itcteam.kalkulatorpks.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTools {

    Date startDate, endDate;

    public DateTools(Date start, Date end) {

        this.startDate = start;
        this.endDate = end;

    }

    public int getDaysDifference()
    {
        if(this.startDate==null||this.endDate==null)
            return 0;

        return (int)( (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
    }

}
