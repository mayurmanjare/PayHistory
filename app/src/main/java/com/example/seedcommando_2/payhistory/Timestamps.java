package com.example.seedcommando_2.payhistory;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by seedcommando_2 on 12/21/2016.
 */

public class Timestamps {

    public String getdata(long time) {
       try
        {
            Calendar cal=Calendar.getInstance();
            cal.setTimeInMillis(time);
            int year= cal.get(Calendar.YEAR);
            int month=cal.get(Calendar.MONTH);
            int day=cal.get(Calendar.DAY_OF_MONTH);
            SimpleDateFormat sdf=new SimpleDateFormat("hh:mm   a");
            System.out.println(sdf.format(cal.getTime()));
                String[] monthName={"Jan","Feb","Mar","April","May","June","July","Augest","Sept","Oct","Nov","Dec"};
                return ""+day+" "+monthName[month]+" "+year+"    "+" "+sdf.format(cal.getTime());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }




}