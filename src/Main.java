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
            Element root = web_XML.getDocumentElement();
            System.out.println(root.getNodeName());
            System.out.println("testing" + root.getAttributes().getNamedItem("num_results").getNodeValue());

            /*NodeList XMLmatches = web_XML.getElementsByTagName("match");
            System.out.println("NAME: " + XMLmatches.item(0).getNodeName());

            Node nodeXML = XMLmatches.item(0);
            System.out.println("nodeXML: " + nodeXML.getNodeName());

            Element matchElement = (Element) XMLmatches.item(0);
            System.out.println("test:" + matchElement.valueOf);*/
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