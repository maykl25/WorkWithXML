package theGame;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import java.util.ArrayList;

public class ReadFileWithXml {
    public static void main(String[] args) {

    }

    public static void printSteps(Document document, ArrayList<String> steps) throws DOMException, XPathExpressionException {

        XPathFactory pathFactory = XPathFactory.newInstance();
        XPath xpath = pathFactory.newXPath();

        XPathExpression expr = xpath.compile("//Game/Step");

        NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            steps.add(n.getTextContent());

        }
        System.out.println();
    }

}
