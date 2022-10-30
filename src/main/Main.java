package src.main;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String compname = "CambridgeChallenge2020";
    private static final String filename = "WCA_export_Results.tsv"; //path to the "WCA_export_Results" file
    private static final String compFileName = "WCA_export_Competitions.tsv"; //path to the "WCA_export_Competitions" file
    private static final String event = "333fm";
    private static final String eventtype = "Ao5"; //Must be Ao5, Mo3, Bo3, Bo2, or Bo1
    private static final int days = 365;
    private static final int people = 5; //# of top competitors to consider
    private static final int simulationsrun = 1000;

    public static void main(String[] args) {

        WebScraper ws = new WebScraper(compname, event);
        List<String> ids = ws.getCompetitors(people);
        List<String> names = ws.getNames(people);
        List<Competitor> competitors = new ArrayList<>();
        for (int i = 0; i < people; i++) {
            competitors.add(new Competitor(names.get(i), ids.get(i), filename, event, days, compFileName));
            System.out.println("Found results for " + names.get(i) + ".");
            competitors.get(i).calcvalues();
        }
        int resultstocalc = Integer.parseInt(eventtype.substring(2));
        for (int i = 0; i < simulationsrun; i++) {
            List<Double> averages = new ArrayList<>();
            for (int j = 0; j < people; j++) {
                List<Double> times = new ArrayList<>();
                for (int k = 0; k < resultstocalc; k++) {
                    //calc results

                }
            }
        }
    }
}
