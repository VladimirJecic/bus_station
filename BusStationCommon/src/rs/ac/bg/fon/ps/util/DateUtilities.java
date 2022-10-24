/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import rs.ac.bg.fon.ps.domain.Departure;
import rs.ac.bg.fon.ps.domain.Line;

/**
 *
 * @author Vladimir Jecić
 */
public class DateUtilities {
//<editor-fold defaultstate="collapsed" desc="comment">
//    private DateUtilities instance;
//    private SimpleDateFormat formater;
//
//    private DateUtilities() {
//        formater = new SimpleDateFormat("dd.MM.yyyy HH:mm");
//    }
//    public DateUtilities getInstace(){
//        if(instance ==null){
//            instance = new DateUtilities();
//        }
//        return instance;
//    }
//</editor-fold>

    public static Date format(String string) throws ParseException {
        Date date = null;
        SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        date = formater.parse(string);
        return date;

    }

    public static Date format(String string, String format) throws ParseException {
        Date date = null;
        SimpleDateFormat formater = new SimpleDateFormat(format);
        date = formater.parse(string);
        return date;

    }

    public static String parse(Date date) {
        String string = "";
        SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        string = formater.format(date);
        return string;
    }

    public static String parse(Date date, String format) throws ParseException {
        String string = "";
        SimpleDateFormat formater = new SimpleDateFormat(format);
        string = formater.format(date);
        return string;
    }

    public static String calculateMeanLineTravelTime(Line line) throws ParseException {
        if (line == null || line.getDepartures().isEmpty()) {
            return line.getTravelTime();
        }
        long arrivalLong = 0L;
        long departureLong = 0L;
        for (Departure departure : line.getDepartures()) {
            departureLong += departure.getDepartureTime().getTime();
            arrivalLong += departure.getArrivalTime().getTime();
        }
        long difference = (arrivalLong - departureLong) / line.getDepartures().size();
        int hours = (int) (difference / (3600 * 1000));
        int minutes = (int) ((difference / (60 * 1000)) % 60);
        String stringTime = (hours > 9 ? hours : hours) + "h and " + (minutes > 9 ? minutes : "0" + minutes)+"min";
        return stringTime;
    }
}
