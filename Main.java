import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        String compname = "CambridgeChallenge2020";
        String filename = "C:\\Users\\Nicholas\\Desktop\\WCA_export_Results.tsv";
        String compFileName = "C:\\Users\\Nicholas\\Desktop\\WCA_export_Competitions.tsv";
        String event = "333fm";
        String eventtype = "Ao5"; //Must be Ao5, Mo3, Bo3, Bo2, or Bo1
        int days = 365;
        int people = 5;
        int simulationsrun = 1000;
        WebScraper wS = new WebScraper(compname, event);
        List<String> ids = wS.getCompetitors(people);
        List<String> names = wS.getNames(people);
        List<Competitor> competitors = new ArrayList<>();
        for(int i = 0; i < people; i++) {
            competitors.add(new Competitor(names.get(i), ids.get(i), filename, event, days, compFileName));
            System.out.println("Found results for " + names.get(i) + ".");
            competitors.get(i).calcvalues();
        }
        int resultstocalc = Integer.parseInt(eventtype.substring(2));
        for(int i = 0; i < simulationsrun; i++) {
            List<Double> averages = new ArrayList<>();
            for(int j = 0; j < people; j++) {
                List<Double> times = new ArrayList<>();
                for(int k = 0; k < resultstocalc; k++) {
                    //calc results

                }
            }
        }
    }

}
