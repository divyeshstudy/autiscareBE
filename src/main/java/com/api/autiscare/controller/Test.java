package com.api.autiscare.controller;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Test {
    public static void main(String[] args) {
        String numberD = String.valueOf("0.5695");
        numberD = numberD.substring(numberD.indexOf(".")+1);
        System.out.println("number = " + numberD);
        int length = numberD.length();
        if (length == 7) {
            System.out.println(numberD);
        }
        else
            if(length > 7)
            {
                System.out.println(numberD.substring(0,7));
            }
            else
            if(length < 7)
            {
                String zeros = "0";
                for (int i=1;i< (7-numberD.length());i++){
                    zeros = zeros.concat("0");
                }
                System.out.println(numberD.concat(zeros));
            }
    }
}
