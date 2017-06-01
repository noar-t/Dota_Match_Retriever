import jdk.nashorn.internal.runtime.ParserException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Main {

    public static void main(String[] args) throws Exception  {
        System.out.println("Hello World!");
        //File dota_XML = new File

        String apikey = ""; // need to read from file

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document web_XML = dBuilder.parse("https://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/V001/?format=XML&key="+apikey);
            Element root = web_XML.getDocumentElement();
            System.out.print(root.getNodeName());
        }
        catch (ParserException e) {
            System.out.println("error\n");
        }
    }
}