package ui.ConsoleInteraction;

import org.json.JSONException;
import ui.ConsoleInteraction.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class ReadWebPageEx {

    public static void main(String[] args) throws IOException {

        BufferedReader br = null;

        try {
            String theURL = "https://thereportoftheweek-api.herokuapp.com/reports?between=2012-1-1%7C2012-2-28";
            URL url = new URL(theURL);
            br = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;

            StringBuilder sb = new StringBuilder();

            while((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            Parser parser = new Parser();
            try {
                parser.parse(sb.toString());
            } catch (JSONException e) {
                System.out.println("error reading file");
            }

        } finally {

            if (br != null) {
                br.close();
            }
        }
    }


}

