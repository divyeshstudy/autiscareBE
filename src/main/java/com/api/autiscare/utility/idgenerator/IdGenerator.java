package com.api.autiscare.utility.idgenerator;

import java.io.Serializable;

/**
 * @author divyesh.dwivedi
 */

public class IdGenerator {

    public static String getChildId()
    {
        return "C".concat(getIdentifier());
    }

    public static String getTheraphyCenterId()
    {
        return "TC".concat(getIdentifier());
    }

    public static String getTheraphistId()
    {
        return "T".concat(getIdentifier());
    }

    public static String getUserId()
    {
        return "A".concat(getIdentifier());
    }

    public static String getUserMappingId()
    {
        return "U".concat(getIdentifier());
    }

    public static String getPaymentId()
    {
        return "P".concat(getIdentifier());
    }

    public static String getTherapySessionId()
    {
        System.out.println("getTherapySessionId = " + "TSID".concat(getIdentifier()));

        return "TSID".concat(getIdentifier());
    }

    private static String getIdentifier(){
        Double randomNo = Math.random();
        String numberD = String.valueOf(randomNo);
        numberD = numberD.substring(numberD.indexOf(".")+1);
        int length = numberD.length();
        if(length > 7)
        {
            numberD = numberD.substring(0,7);
        }
        else
        if(length < 7)
        {
            String zeros = "0";
            for (int i=1;i< (7-numberD.length());i++){
                zeros = zeros.concat("0");
            }
            numberD = numberD.concat(zeros);
        }
        return numberD;
    }
}
