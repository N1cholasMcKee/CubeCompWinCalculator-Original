package src.main;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.*;
import java.time.Clock;
import java.time.LocalDate;

public class DBReader {

    private List<String> lineResults = new ArrayList<>();
    private List<String> comp = new ArrayList<>();
    private List<List<Integer>> times = new ArrayList<>();
    private List<Integer> daysPassed = new ArrayList<>();

    private String pathname;
    private String id;
    private String event;

    public DBReader(String pathname, String id) {
        this.pathname = pathname;
        this.id = id;
    }

    public void readDB(String event) {
        this.event = event;
        Pattern cre = null;
        BufferedReader inputStream = null;
        try {
            cre = Pattern.compile(id);
            inputStream = new BufferedReader(new FileReader(pathname));
            String l;
            while ((l = inputStream.readLine()) != null) {
                Matcher m = cre.matcher(l);
                if(m.find()) {
                    if (l.substring(l.indexOf("\t") + 1, l.indexOf("\t") + event.length() + 2).equals(event.concat("\t"))) {
                        lineResults.add(l);
                        comp.add(l.substring(0, l.indexOf("\t")));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("File not found, please try again");

        }
    }

    public List<Integer> compDateFinder(String pathname) {
        Pattern cre = null;
        BufferedReader inputStream = null;
        try {
            inputStream = new BufferedReader(new FileReader(pathname));
            String l;
            while ((l = inputStream.readLine()) != null) {
                for(String s : comp) {
                    cre = Pattern.compile(s + "\t");
                    Matcher m = cre.matcher(l);
                    if(m.find()) {
                        if(l.substring(0, s.length()).equals(s)) {
                            String d = l.substring(0, l.lastIndexOf("\t", l.indexOf(event)));
                            int endday = Integer.parseInt(d.substring(d.lastIndexOf("\t")+1));
                            d = d.substring(0, d.lastIndexOf("\t"));
                            int endmonth = Integer.parseInt(d.substring(d.lastIndexOf("\t")+1));
                            for(int i=0; i<3; i++) {
                                d = d.substring(0, d.lastIndexOf("\t"));
                            }
                            int year = Integer.parseInt(d.substring(d.lastIndexOf("\t")+1));
                            daysPassed.add(calcdayspassed(endday, endmonth, year));
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        Collections.sort(daysPassed);
        Collections.reverse(daysPassed);
        return daysPassed;
    }

    public List<List<Integer>> getTimes(int daycutoff) {
        for(String s : lineResults) {
            String toTimes = s.substring(0, s.lastIndexOf("\t")-1), s2;
            if(toTimes.charAt(toTimes.length()-1) > 57) {
                toTimes = toTimes.substring(0, toTimes.lastIndexOf("\t"));
            }
            ArrayList<Integer> temp = new ArrayList<>(5);
            for(int i = 0; i < 5; i++) {
                s2 = toTimes.substring(toTimes.lastIndexOf("\t")+1);
                toTimes = toTimes.substring(1, toTimes.lastIndexOf("\t"));
                if(! (s2.equals("0") || s2.equals("-2")))
                    temp.add(Integer.parseInt(s2));
            }
            times.add(temp);
        }
        if(daysPassed.get(daysPassed.size()-1) > daycutoff) {
            while (times.size() > 5) {
                times.remove(0);
                daysPassed.remove(0);
            }
        } else {
            while (daysPassed.get(0) >= daycutoff) {
                times.remove(0);
                daysPassed.remove(0);
            }
        }
        return times;
    }

    private int calcdayspassed(int day, int month, int year) {
        int mDaysIntoYear = 0;
        LocalDate today = LocalDate.now(Clock.systemDefaultZone());
        switch (month) {
            case 1:
                mDaysIntoYear = 0;
                break;
            case 2:
                mDaysIntoYear = 31;
                break;
            case 3:
                mDaysIntoYear = 59;
                break;
            case 4:
                mDaysIntoYear = 90;
                break;
            case 5:
                mDaysIntoYear = 120;
                break;
            case 6:
                mDaysIntoYear = 151;
                break;
            case 7:
                mDaysIntoYear = 181;
                break;
            case 8:
                mDaysIntoYear = 212;
                break;
            case 9:
                mDaysIntoYear = 243;
                break;
            case 10:
                mDaysIntoYear = 273;
                break;
            case 11:
                mDaysIntoYear = 301;
                break;
            case 12:
                mDaysIntoYear = 334;
                break;
        }
        if(year%4 == 0 && month > 2);
            mDaysIntoYear++;
        int doty = mDaysIntoYear + day;
        return (int) ((((double) today.getYear()*365.25) + today.getDayOfYear()) - (((double) year*365.25) + doty));
    }
}
