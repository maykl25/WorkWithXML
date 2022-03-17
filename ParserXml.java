package theGame;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.dom.DOMSource;
import java.io.File;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Node;

public class ParserXml {
    public static void main(String[] args) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        Document doc = factory.newDocumentBuilder().newDocument();

     

    }

    public static Node getDraw(Document doc) {
        Element result = doc.createElement("GameResult");
        result.appendChild(doc.createTextNode("Draw!"));
        return result;
    }

    public static Node getResult(Document doc, String id, String name, String symbol) {
        Element result = doc.createElement("GameResult");

        result.appendChild(getPlayer(doc, id, name, symbol));

        return result;
    }

    public static Node getStep(Document doc, String num, String playerId, String location) {
        Element step = doc.createElement("Step");
        step.appendChild(doc.createTextNode(location));
        step.setAttribute("num", num);
        step.setAttribute("playerId", playerId);
        return step;

    }

    public static Node getPlayer (Document doc, String id, String name, String symbol) {
        Element player = doc.createElement("Player");

        player.setAttribute("id", id);

        player.setAttribute("name", name);

        player.setAttribute("symbol", symbol);
        return player;
    }
}
