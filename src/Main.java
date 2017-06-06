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
        getMatchList();

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

    public static void getMatchList() throws Exception {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document web_XML = dBuilder.parse("https://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/V001/?format=XML&account_id=" + mAccountId + "&key=" + mApiKey);
            web_XML.getDocumentElement().normalize();
        }
        catch (IOException e) {
            System.out.println("HTTP Request failed, check api key\n");
        }


    }

    public static ArrayList<Match> getMatchList2() throws Exception {
        long mMatchId;
        long[] mPlayerIds = new long[10];
        int[] mPlayerHeros = new int[10];
        ArrayList<Match> mMatchArray = new ArrayList<>();

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document web_XML = dBuilder.parse("https://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/V001/?format=XML&account_id=" + mAccountId + "&key="+ mApiKey);
            web_XML.getDocumentElement().normalize();

            NodeList XMLmatches = web_XML.getElementsByTagName("match");

            for (int temp = 0; temp < XMLmatches.getLength(); temp++) { // loop through each match in xml
                Node nNode = XMLmatches.item(temp);
                System.out.println("==========================");
                System.out.println("Element         " + temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    mMatchId = Long.parseLong(
                            eElement
                                    .getElementsByTagName("match_id")
                                    .item(0)
                                    .getTextContent());

                    NodeList XMLplayers = eElement.getElementsByTagName("player");

                    for (int i = 0; i < XMLplayers.getLength(); i++) { // loop through each player in each match
                        if (XMLplayers.getLength() < 10) {
                            mMatchId = -mMatchId;
                            break;
                        }
                        Element test = (Element) XMLplayers.item(i);

                        mPlayerIds[i] = Long.parseLong(test
                                .getElementsByTagName("account_id")
                                .item(0)
                                .getTextContent());

                        mPlayerHeros[i] = Integer.parseInt(test
                                .getElementsByTagName("hero_id")
                                .item(0)
                                .getTextContent());
                    }

                    mMatchArray.add(new Match(mMatchId, mPlayerIds, mPlayerHeros));
                    System.out.println(mMatchArray.get(temp));
                }
            }
        }
        catch (ParserException e ) {
            System.out.println("Error in XML\n");
        }


    }

    public int getMatchDetails (long matchId) throws Exception{

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document web_XML = dBuilder.parse("https://api.steampowered.com/IDOTA2Match_570/GetMatchDetails/V001/?format=XML&match_id=" + matchId + "&key="+ apiKey);
            web_XML.getDocumentElement().normalize();

            NodeList XMLmatches = web_XML.getElementsByTagName("match");

            for (int temp = 0; temp < XMLmatches.getLength(); temp++) { // loop through each match in xml
                Node nNode = XMLmatches.item(temp);
                System.out.println("==========================");
                System.out.println("Element         " + temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    mMatchId = Long.parseLong(
                            eElement
                                    .getElementsByTagName("match_id")
                                    .item(0)
                                    .getTextContent());

                    NodeList XMLplayers = eElement.getElementsByTagName("player");

                    for (int i = 0; i < XMLplayers.getLength(); i++) { // loop through each player in each match
                        if (XMLplayers.getLength() < 10) {
                            mMatchId = -mMatchId;
                            break;
                        }
                        Element test = (Element) XMLplayers.item(i);

                        mPlayerIds[i] = Long.parseLong(test
                                .getElementsByTagName("account_id")
                                .item(0)
                                .getTextContent());

                        mPlayerHeros[i] = Integer.parseInt(test
                                .getElementsByTagName("hero_id")
                                .item(0)
                                .getTextContent());
                    }

                    mMatchArray.add(new Match(mMatchId, mPlayerIds, mPlayerHeros));
                    System.out.println(mMatchArray.get(temp));
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not present or file in wrong directory\n");
        }
        catch (ParserException e ) {
            System.out.println("Error in XML\n");
        }
        catch (IOException e) {
            System.out.println("HTTP Request failed, check api key\n");
        }


    }
}