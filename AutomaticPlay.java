package theGame;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;

public class AutomaticPlay {
    public static char[][] gameField = new char[3][3];

    static char firstSymbol = 'X';
    static char secondSymbol = 'O';
    static char initialSymbol = '.';

    static ArrayList<String> list = new ArrayList<>();
    public static ArrayList<String> attributes = new ArrayList<>();


    public static void main(String[] args) {

        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse("src/test.xml");

            ReadFileWithXml.printSteps(document, list);
            ReadFileWithXml.printPlayers(document, attributes);

        } catch (XPathExpressionException | ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace(System.out);
        }

        int x;
        int y;
        initialGameField();
        printField();
        System.out.println();
        for (int i = 0; i < list.size(); i++) {
            if (i % 2 == 0) {
                String[] everyStep = list.get(i).split(" ");
                x = Integer.parseInt(everyStep[0]);
                y = Integer.parseInt(everyStep[1]);
                firstPlayerStep(x, y);
                printField();
                System.out.println();
            } else if (i % 2 != 0){
                String[] everyStep = list.get(i).split(" ");
                x = Integer.parseInt(everyStep[0]);
                y = Integer.parseInt(everyStep[1]);
                secondPlayerStep(x, y);
                printField();
                System.out.println();
            }
        }
        for (String element : attributes) {
            System.out.println(element);
        }
    }
    public static void initialGameField() {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++) {
                gameField[i][j] = initialSymbol;
            }
        }
    }
    public static void printField() {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++) {
                System.out.print(gameField[i][j]);
            }
            System.out.println();
        }
    }

    public static void firstPlayerStep(int x, int y) {
        setSym(y,x, firstSymbol);

    }
    public static void secondPlayerStep(int x, int y) {
        setSym(y,x, secondSymbol);

    }

    public static void setSym(int y, int x, char sym) {
        gameField[y][x] = sym;
    }
}
