package src.main;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.*;

public class WebScraper {

    private static String compurl;
    private static List<String> competitors = new ArrayList<>();

    WebScraper(String compid, String event) {
        compurl = "https://www.worldcubeassociation.org/competitions/" + compid + "/registrations/psych-sheet/" + event;
    }

    public List<String> getCompetitors(int num) {
        Document doc = null;
        try {
            doc = Jsoup.connect(compurl).get();
            String title = doc.title();
            Elements links = doc.select("a[href]");
            int count = 0;
            for (Element link : links) {
                if(count < num) {
                    String s = link.attr("abs:href");
                    if (s.length() > 46 && s.substring(0, 45).equals("https://www.worldcubeassociation.org/persons/")) {
                        competitors.add(s.substring(45));
                        count++;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return competitors;
    }

    public List<String> getNames(int num) {
        List<String> names = new ArrayList<>(num);
        Document doc = null;
        try {
            for(int i = 0; i < num; i++) {
                String url = "https://www.worldcubeassociation.org/persons/" + competitors.get(i);
                doc = Jsoup.connect(url).get();
                String title = doc.title();
                names.add(title.substring(0, title.indexOf("|")-1));
            }

        } catch (IOException e) {
            System.out.println(e);
        }
        return names;
    }
}
