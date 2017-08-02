//import com.sun.tools.javac.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;

public class Main {

    private static String mApiKey;    // unique api key to make requests
    private static String mAccountId; // id to make request
    private static String mSteamId3;  // id to parse from match data

    public static void main(String[] args) throws Exception  {
        //javafx.util.Pair testOPair = new Pair("testing123", 123);
       /// Pair testPair = new Pair(5,7);
        //System.out.println("left"+ testOPair.getKey() + "right" + testOPair.getValue());
        getDevValues();
        DatabaseHandler dbHandler = new DatabaseHandler();

        //testing
        //Match testMatch = new Match(25,true,13,14,null);
        //dbHandler.databaseAddMatch(testMatch);

        /*Document XML = getMatchListXML("https://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/V001/?format=XML&account_id="
                + mAccountId
                + "&key="
                + mApiKey);

        ArrayList<Long> mMatches = null;
        mMatches = getMatchArrayList(XML); // returns long array of match id

        int totalCount = 0;
        int outputTestInt = 0;
        ArrayList<Match> matchObjects = new ArrayList<>();
        if (mMatches != null) {
            for (Long i : mMatches) {
                Thread.sleep(100);
                matchObjects.add(getMatchDetails(i));
                System.out.println(matchObjects.get(outputTestInt) != null
                        ? matchObjects.get(outputTestInt)
                        : "Bad Match");
                outputTestInt++;
                totalCount++;
            }
            for (int x = 0; x < 4; x++) {
                outputTestInt = 0;
                XML = getMatchListXML("https://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/V001/?format=XML&start_at_match_id="
                        + mMatches.get(mMatches.size() - 1)
                        + "&account_id="
                        + mAccountId
                        + "&key="
                        + mApiKey);
                mMatches = getMatchArrayList(XML);
                mMatches.remove(0);

                for (Long i : mMatches) {
                    Thread.sleep(100);
                    matchObjects.add(getMatchDetails(i));
                    System.out.println(matchObjects.get(outputTestInt) != null
                            ? matchObjects.get(outputTestInt)
                            : "Bad Match");
                    outputTestInt++;
                    totalCount++;
                }
            }
        }
        System.out.println("END TOTAL = " + totalCount
                + "\nARRAY SIZE = " + matchObjects.size());*/

        dbHandler.close();

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

    @Nullable
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

    @Contract("null -> null")
    public static ArrayList<Long> getMatchArrayList(Document XML) { // returns a list of the last 100 matches

        if (XML == null)
            return null;

        long MatchId;
        ArrayList<Long> matchArray = new ArrayList<>();

        NodeList XMLmatches = XML.getElementsByTagName("match");

        for (int temp = 0; temp < XMLmatches.getLength(); temp++) { // loop through each match in xml
            Node nNode = XMLmatches.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                MatchId = Long
                        .parseLong(eElement
                                .getElementsByTagName("match_id")
                                .item(0)
                                .getTextContent());

                NodeList XMLplayers = eElement.getElementsByTagName("player");

                if (XMLplayers.getLength() == 10)
                    matchArray.add(MatchId);
                else
                    matchArray.add(-MatchId);
            }
        }

        return matchArray;

    }

    @Nullable
    public static Match getMatchDetails (Long matchId) throws Exception {

        if (matchId <= 0)
            return null;

        Document XML = getMatchListXML("https://api.steampowered.com/IDOTA2Match_570/GetMatchDetails/V001/?format=XML&match_id="
                + matchId
                + "&key="
                + mApiKey);

        boolean radiantWin = Boolean
                .parseBoolean(XML
                    .getElementsByTagName("radiant_win")
                    .item(0)
                    .getTextContent());

        int radiantScore = Integer
                .parseInt(XML
                    .getElementsByTagName("radiant_score")
                    .item(0)
                    .getTextContent());

        int direScore = Integer
                .parseInt(XML
                    .getElementsByTagName("dire_score")
                    .item(0)
                    .getTextContent());

        // not sure best way to convert seconds to min:sec
        /*System.out.println(XML
                .getElementsByTagName("duration")
                .item(0)
                .getTextContent());*/

        NodeList XML_Players = XML.getElementsByTagName("player");

        long accountId;
        int playerSlot;
        int playerHeroId;
        ArrayList<Player> matchPlayers = new ArrayList<>();

        for (int temp = 0; temp < XML_Players.getLength(); temp++) { // loop through each match in xml

            Element playerElement = (Element) XML_Players.item(temp);

            accountId = Long
                    .parseLong(playerElement
                        .getElementsByTagName("account_id")
                        .item(0)
                        .getTextContent());

//need to add player slot to
            playerSlot = Integer
                    .parseInt(playerElement.getElementsByTagName("player_slot")
                        .item(0)
                        .getTextContent());


            playerHeroId = Integer
                    .parseInt(playerElement
                        .getElementsByTagName("hero_id")
                        .item(0)
                        .getTextContent());


            int[] playerItems = new int[6];
            for (int item_num = 0; item_num < 6; item_num++) {
                playerItems[item_num] = Integer
                        .parseInt(playerElement
                            .getElementsByTagName("item_" + item_num)
                            .item(0)
                            .getTextContent());
            }

            int[] backpackPlayerItems = new int[3];
            for (int backpack_num = 0; backpack_num < 3; backpack_num++) {
                backpackPlayerItems[backpack_num] = Integer
                        .parseInt(playerElement
                            .getElementsByTagName("backpack_" + backpack_num)
                            .item(0)
                            .getTextContent());
            }
            matchPlayers.add(new Player(accountId, playerHeroId, playerSlot, backpackPlayerItems, playerItems));
        }
        //System.out.println(new Match(matchId, radiantWin, radiantScore, direScore, matchPlayers));
        return new Match(matchId, radiantWin, radiantScore, direScore, matchPlayers);
    }

    public static double calcWinRate(ArrayList<Match> Matches, long AccountId) {
        double wins = 0;
        double total = 0;

        for (Match i: Matches) {
            if (i != null) {
                if (i.isWinner(AccountId)) {
                    System.out.println("A win");
                    wins++;
                    total++;
                }
                else {
                    System.out.println("A loss");
                    total++;
                }
            }
        }

        return wins / total;
    }
}