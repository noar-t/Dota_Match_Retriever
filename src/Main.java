import jdk.nashorn.internal.runtime.ParserException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


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
            System.out.print(root.getNodeName());
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