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

            //System.out.println("testing" + root.getAttributes().getNamedItem("num_results").getNodeValue());

            NodeList XMLmatches = web_XML.getElementsByTagName("match");

            for (int temp = 0; temp < XMLmatches.getLength(); temp++) {
                Node nNode = XMLmatches.item(temp);
                System.out.println("\nElement " + temp + " :"
                        + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    /*System.out.println("Student roll no : "
                            + eElement.getAttribute("rollno"));*/
                    System.out.println("Match ID : "
                            + eElement
                            .getElementsByTagName("match_id")
                            .item(0)
                            .getTextContent());
                    NodeList XMLplayers = eElement.getElementsByTagName("player");
                    System.out.println("length" + XMLplayers.getLength());
                    for (int i = 0; i < XMLplayers.getLength(); i++) {
                        Element test = (Element) XMLplayers.item(i);
                        System.out.println(test.getElementsByTagName("account_id").item(0).getTextContent());
                    }
                    /*System.out.println("Last Name : "
                            + eElement
                            .getElementsByTagName("player")
                            .item(0)
                            .getTextContent());*/
                    /*System.out.println("Nick Name : "
                            + eElement
                            .getElementsByTagName("nickname")
                            .item(0)
                            .getTextContent());
                    System.out.println("Marks : "
                            + eElement
                            .getElementsByTagName("marks")
                            .item(0)
                            .getTextContent());*/
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


       /* try {
            File inputFile = new File("input.txt");
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :"
                    + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("student");

            System.out.println("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :"
                        + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("Student roll no : "
                            + eElement.getAttribute("rollno"));
                    System.out.println("First Name : "
                            + eElement
                            .getElementsByTagName("firstname")
                            .item(0)
                            .getTextContent());
                    System.out.println("Last Name : "
                            + eElement
                            .getElementsByTagName("lastname")
                            .item(0)
                            .getTextContent());
                    System.out.println("Nick Name : "
                            + eElement
                            .getElementsByTagName("nickname")
                            .item(0)
                            .getTextContent());
                    System.out.println("Marks : "
                            + eElement
                            .getElementsByTagName("marks")
                            .item(0)
                            .getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}