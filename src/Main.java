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

    public static void main(String[] args) throws Exception  {
        long mMatchId;
        long[] mPlayerIds = new long[10];
        int[] mPlayerHeros = new int[10];
        ArrayList<Match> mMatchArray = new ArrayList<>();

        System.out.println("Hello World!");

        //String apiKey = "";

        try {
            File apiKeyFile = new File("DevKey.txtkey");
            BufferedReader lineReader = new BufferedReader(new FileReader(apiKeyFile));
            String apiKey = lineReader.readLine(); // need to read from file
            String accountId = lineReader.readLine();
            String steamId3 = lineReader.readLine();
            //System.out.println(apiKey +"\n" + accountId);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document web_XML = dBuilder.parse("https://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/V001/?format=XML&account_id=" + accountId + "&key="+ apiKey);
            web_XML.getDocumentElement().normalize();

            //System.out.println("Root element: "
            //        + web_XML.getDocumentElement().getNodeName());

            NodeList XMLmatches = web_XML.getElementsByTagName("match");

            for (int temp = 0; temp < XMLmatches.getLength(); temp++) { // loop through each match in xml
                Node nNode = XMLmatches.item(temp);
                System.out.println("_______________________________");
                System.out.println("Element " + temp + " : "
                        + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    mMatchId = Long.parseLong(
                            eElement
                            .getElementsByTagName("match_id")
                            .item(0)
                            .getTextContent());

                    //System.out.println("Match ID : "
                    //        + mMatchId);

                    NodeList XMLplayers = eElement.getElementsByTagName("player");

                    for (int i = 0; i < XMLplayers.getLength(); i++) { // loop through each player in each match
                        Element test = (Element) XMLplayers.item(i);

                        mPlayerIds[i] = Long.parseLong(test
                                .getElementsByTagName("account_id")
                                .item(0)
                                .getTextContent());

                        //System.out.println("Account ID " + i + ": " + mPlayerIds[i]);

                        mPlayerHeros[i] = Integer.parseInt(test
                                .getElementsByTagName("hero_id")
                                .item(0)
                                .getTextContent());

                        //System.out.println("Hero ID " + i + ": " + mPlayerHeros[i]);

                        //System.out.println("Player Slot : " + i);
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