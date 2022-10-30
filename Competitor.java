import java.util.List;
import java.util.ArrayList;
import java.util.PrimitiveIterator;

public class Competitor {

    private List<List<Integer>> times;
    private List<Integer> compdates;
    private String name;
    private String WCAID;
    private double weighted_average, weighted_dnf_rate, standardDeviation, median;
    private final double weightFactor = .98;
    public int wins = 0;
    public int podiums = 0;

    public Competitor(String name, String WCAID, String filename, String event, int days, String compFileName) {
        this.name = name;
        this.WCAID = WCAID;
        DBReader DBr = new DBReader(filename, this.WCAID);
        DBr.readDB(event);
        compdates = DBr.compDateFinder(compFileName);
        this.times = DBr.getTimes(days);
    }


    public void calcvalues() {
        System.out.println(times);
        double totalweight = 0, dnftotal = 0, finalsum = 0, weight;
        for(int i = 0; i < times.size(); i++) {
            int timescount = 0;
            double total = 0,  dnfcount = 0;
            for(int j=0; j < times.get(i).size(); j++) {
                if (times.get(i).get(j) != -1) {
                    timescount++;
                    total += times.get(i).get(j);
                } else {
                    dnfcount++;
                }
            }
            total /= timescount;
            dnfcount /= timescount;
            weight =  Math.pow(weightFactor, compdates.get(i));
            totalweight += weight;
            finalsum += weight*total;
            dnftotal += weight*dnfcount;
        }
        weighted_average = finalsum/(totalweight);
        weighted_dnf_rate = dnftotal/(totalweight);
        System.out.println("Information for " + name + "\n" + getAverage());
    }

    public double getAverage() {
        return weighted_average;
    }

    public double getDNF() {
        return weighted_dnf_rate;
    }

    public void addwins() {
        wins++;
    }

    public void addpodium() {
        podiums++;
    }

    public int getWins() {
        return wins;
    }

    public int getPodiums() {
        return podiums;
    }

}
