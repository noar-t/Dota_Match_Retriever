import jdk.nashorn.internal.runtime.ParserException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

public class Main {

    public static void main(String[] args) throws Exception  {
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

            System.out.println("Root element: "
                    + web_XML.getDocumentElement().getNodeName());

            NodeList XMLmatches = web_XML.getElementsByTagName("match");

            for (int temp = 0; temp < XMLmatches.getLength(); temp++) {
                Node nNode = XMLmatches.item(temp);
                System.out.println("\nElement " + temp + " :"
                        + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    System.out.println("Match ID : "
                            + eElement
                            .getElementsByTagName("match_id")
                            .item(0)
                            .getTextContent());
                    NodeList XMLplayers = eElement.getElementsByTagName("player");

                    for (int i = 0; i < XMLplayers.getLength(); i++) {
                        Element test = (Element) XMLplayers.item(i);

                        System.out.println("Account ID " + i + ": " + test
                                .getElementsByTagName("account_id")
                                .item(0)
                                .getTextContent());

                        System.out.println("Hero ID " + i + ": " + test
                                .getElementsByTagName("hero_id")
                                .item(0)
                                .getTextContent());
                    }

                }

            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not present or file in wrong directory");
        }
        catch (ParserException e ) {
            System.out.println("Error in XML\n");
        }
        catch (IOException e) {
            System.out.println("HTTP Request failed, check api key");
        }

    }
}