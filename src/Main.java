import jdk.nashorn.internal.runtime.ParserException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;

public class Main {

    private static String mApiKey;
    private static String mAccountId;
    private static String mSteamId3;

    public static void main(String[] args) throws Exception  {
        getDevValues();
        Document XML = getMatchListXML("https://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/V001/?format=XML&account_id=" + mAccountId + "&key=" + mApiKey);
        ArrayList<Long> mMatches = null;
        if (XML != null)
            mMatches = getMatchArrayList(XML);

        if (mMatches != null)
            for (Long i : mMatches)
                System.out.println(i);

    }

    public static void getDevValues() throws Exception {
        try {
            File apiKeyFile = new File("DevKey.txtkey");
            BufferedReader lineReader = new BufferedReader(new FileReader(apiKeyFile));
            mApiKey = lineReader.readLine(); // need to read from file
            mAccountId = lineReader.readLine();
            mSteamId3 = lineReader.readLine();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not present or file in wrong directory\n");
        }
    }

    public static Document getMatchListXML(String request) throws Exception {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document web_XML = dBuilder.parse(request);
            web_XML.getDocumentElement().normalize();
            return web_XML;
        }
        catch (IOException e) {
            System.out.println("HTTP Request failed, check api key\n");
        }
        return null;
    }

    public static ArrayList<Long> getMatchArrayList(Document XML) { // returns a list of the last 100 matches
        long MatchId;
        ArrayList<Long> MatchArray = new ArrayList<>();

        NodeList XMLmatches = XML.getElementsByTagName("match");

        for (int temp = 0; temp < XMLmatches.getLength(); temp++) { // loop through each match in xml
            Node nNode = XMLmatches.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                MatchId = Long.parseLong(eElement
                                .getElementsByTagName("match_id")
                                .item(0)
                                .getTextContent());

                NodeList XMLplayers = eElement.getElementsByTagName("player");

                if (XMLplayers.getLength() == 10)
                    MatchArray.add(MatchId);
                else
                    MatchArray.add(-MatchId);
            }
        }
        return MatchArray;


    }

    public ArrayList<Match> getMatchDetails (Long matchId) throws Exception {
        ArrayList<Match> MatchArray = new ArrayList<>();


        Document XML = getMatchListXML("https://api.steampowered.com/IDOTA2Match_570/GetMatchDetails/V001/?format=XML&match_id=" + matchId + "&key="+ mApiKey);


        NodeList XML_Players = XML.getElementsByTagName("player");

        for (int temp = 0; temp < XML_Players.getLength(); temp++) { // loop through each match in xml
            long[] PlayerIds = new long[10];
            int[] PlayerHeros = new int[10]

            Node nNode = XML_Players.item(temp);
            System.out.println("==========================");
            System.out.println("Element         " + temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                matchId = Long.parseLong(eElement
                        .getElementsByTagName("match_id")
                        .item(0)
                        .getTextContent());

                NodeList XMLplayers = eElement.getElementsByTagName("player");

                for (int i = 0; i < XMLplayers.getLength(); i++) { // loop through each player in each match
                    if (XMLplayers.getLength() < 10) {
                        matchId = -matchId;
                        break;
                    }
                    Element test = (Element) XMLplayers.item(i);

                    PlayerIds[i] = Long.parseLong(test
                            .getElementsByTagName("account_id")
                            .item(0)
                            .getTextContent());

                    PlayerHeros[i] = Integer.parseInt(test
                            .getElementsByTagName("hero_id")
                            .item(0)
                            .getTextContent());
                }

                MatchArray.add(new Match(matchId, PlayerIds, PlayerHeros));
                System.out.println(MatchArray.get(temp));
            }
        }

        return null;
    }
}