import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.sql.*;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;

public class Main {

    private static String mApiKey;    // unique api key to make requests
    private static String mAccountId; // id to make request
    private static String mSteamId3;  // id to parse from match data
    private static String mDatabasePath = "jdbc:sqlite:test.db";

    public static void main(String[] args) throws Exception  {
        getDevValues();
        Connection database = establishDatabase();
        Match testMatch = new Match(25,true,13,14,null);
        databaseAddMatch(database, testMatch);

        //Document XML = getMatchListXML("https://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/V001/?format=XML&account_id="
        //        + mAccountId
        //        + "&key="
        //        + mApiKey);

        ArrayList<Long> mMatches = null;
        //mMatches = getMatchArrayList(XML); // returns long array of match id

        int totalCount = 0;
        int outputTestInt = 0;
        ArrayList<Match> matchObjects = new ArrayList<>();
        /*if (mMatches != null) {
            for (Long i : mMatches) {
                Thread.sleep(100);
                matchObjects.add(getMatchDetails(i));
                System.out.println(matchObjects.get(outputTestInt) != null
                        ? matchObjects.get(outputTestInt)
                        : "Bad Match");
                outputTestInt++;
                totalCount++;
            }
            for (int x = 0; x < 5; x++) {
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
        }*/
        //System.out.println("END TOTAL = " + totalCount
        //        + "\nARRAY SIZE = " + matchObjects.size());
        //System.out.println("Win Rate Test : " + calcWinRate(matchObjects, Long.parseLong(mAccountId)));

       /* matchObjects.add(getMatchDetails(3256365647l));
        matchObjects.add(getMatchDetails(3254280056l));
        System.out.println("Win Rate Testing : " + calcWinRate(matchObjects, Long.parseLong(mSteamId3)));*/

    }

    public static Connection establishDatabase() {

        String url = mDatabasePath;

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created and or an existing database has been connected.");


                String sql = "CREATE TABLE IF NOT EXISTS matches (\n"
                        + "	match_id INTEGER NOT NULL PRIMARY KEY,\n"
                        + "	radiant_win INTEGER NOT NULL,\n"
                        + "	radiant_score INTEGER NOT NULL,\n"
                        + "	dire_score INTEGER NOT NULL,\n"
                        + "	duration INTEGER\n"
                        + ");";

                Statement stmt = conn.createStatement();
                stmt.execute(sql);


                sql = "CREATE TABLE IF NOT EXISTS playersData (\n"
                        + "	playerId integer NOT NULL,\n"
                        + "	matchId integer NOT NULL,\n" // foreign key? refe rences dotaMatches matchId
                        + "	heroId integer NOT NULL,\n"
                        + "	itemSlot0 INTEGER,\n"
                        + "	itemSlot1 integer,\n"
                        + "	itemSlot2 integer,\n"
                        + "	itemSlot3 integer,\n"
                        + "	itemSlot4 integer,\n"
                        + "	itemSlot5 integer\n"
                        + "	backSlot0 integer,\n"
                        + "	backSlot1 integer,\n"
                        + "	backSlot2 integer\n"
                        + ");";
                stmt.execute(sql);

                sql = "CREATE TABLE IF NOT EXISTS players (\n"
                        + "	player_name TEXT,\n"
                        + "	player_id INTEGER NOT NULL,"
                        + "	id INTEGER NOT NULL, \n"
                        + "     FOREIGN KEY (id) REFERENCES matches(match_id)\n"
                        + ");";
                stmt.execute(sql);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (conn != null) {
            return conn;
        }
        return null;
    }

    public static void databaseAddMatch(Connection database, Match match) throws SQLException{
        String sql = "INSERT INTO matches(match_id, radiant_win, radiant_score, dire_score) VALUES(?,?,?,?)";

        PreparedStatement pstmt = database.prepareStatement(sql);
            pstmt.setLong(1, match.getMatchId());
            pstmt.setInt(2, match.mRadiantWin ? 1 : 0);
            pstmt.setInt(3, match.mRadiantScore);
            pstmt.setInt(4, match.mDireScore);
            pstmt.executeUpdate();
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
        int playerHeroId;
        ArrayList<Player> matchPlayers = new ArrayList<>();

        for (int temp = 0; temp < XML_Players.getLength(); temp++) { // loop through each match in xml

            Element playerElement = (Element) XML_Players.item(temp);

            accountId = Long
                    .parseLong(playerElement
                        .getElementsByTagName("account_id")
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
            matchPlayers.add(new Player(accountId, playerHeroId, playerItems, backpackPlayerItems));
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