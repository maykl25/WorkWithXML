package Test;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import theGame.Player;
import theGame.Game;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WriteGameToXml {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        System.out.println("Введите имя первого игрока:");
        Game.firstPlayerName = scanner.nextLine();
        Player one = new Player(1, Game.firstPlayerName, Game.firstPlayerSign);
        System.out.println("Введите имя второго игрока:");
        Game.secondPlayerName = scanner.nextLine();
        Player two = new Player(2, Game.secondPlayerName, Game.secondPlayerSign);
        try {
            builder = factory.newDocumentBuilder();

            Document doc = builder.newDocument();

            Element rootElement =
                    doc.createElementNS("The Game","Gameplay");

            doc.appendChild(rootElement);


            rootElement.appendChild(getPlayer(doc, one.getId() + "", one.getName(), one.getSymbol() + ""));


            rootElement.appendChild(getPlayer(doc, two.getId() + "", two.getName(), two.getSymbol() + ""));

            rootElement.appendChild(getGame(doc, "Game"));

            while (Game.repeat) {
                Game.initialPlayField();
                Game.printField();

                while (true) {
                    Game.firstPlayerStep();
                    one.setLocation(Game.location);
                    System.out.println("Step " + "num= " + Player.step + " playerId= " + one.getId() + " делает ход в локацию " + one.getLocation());
                    rootElement.appendChild(getStep(doc, Player.step + "", one.getId() + "", one.getLocation()));
                    Game.printField();
                    if (Game.checkWin(Game.firstPlayerSign)) {
                        System.out.println(Game.firstPlayerName + " победил(a)!");
                        Game.firstCount +=3 ;
                        rootElement.appendChild(getResult(doc, one.getId() + "", one.getName(), one.getSymbol() + " "));
                        break;
                    }
                    if (Game.isFieldFull()) {
                        System.out.println("Ничья!");
                        Game.firstCount++;
                        rootElement.appendChild(getDraw(doc, "Draw!"));
                        break;
                    }
                    Player.step++;

                    Game.secondPlayerStep();
                    two.setLocation(Game.location);
                    System.out.println("Step " + "num= " + Player.step + " playerId= " + two.getId() + " делает ход в локацию " + two.getLocation());
                    rootElement.appendChild(getStep(doc, Player.step + "", two.getId() + "", two.getLocation()));
                    Game.printField();
                    if (Game.checkWin(Game.secondPlayerSign)) {
                        System.out.println(Game.secondPlayerName + " победил(a)!");
                        Game.secondCount += 3;
                        rootElement.appendChild(getResult(doc, two.getId() + "", two.getName(), two.getSymbol() + " "));
                        break;
                    }
                    if (Game.isFieldFull()) {
                        System.out.println("Ничья!");
                        Game.secondCount++;
                        rootElement.appendChild(getDraw(doc, "Draw!"));
                        break;
                    }
                    Player.step++;
                }
                System.out.println("Хотите сыграть еще?");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String answer = reader.readLine();
                if (answer.equals("No")) {
                    Game.repeat = false;
                }
                Player.step = 1;
            }



            //Результат в виде xml-файла записываем в консоль(для теста) и в файл.
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);


            StreamResult console = new StreamResult(System.out);
            StreamResult file = new StreamResult(new File("/languages.xml"));


            transformer.transform(source, console);
            transformer.transform(source, file);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Node getPlayer(Document doc, String id, String name, String symbol) {
        Element player = doc.createElement("Player");

        player.setAttribute("id", id);

        player.setAttribute("name" , name);

        player.setAttribute("symbol", symbol);
        return player;
    }

    private static Node getGame(Document doc, String name) {
        Element game = doc.createElement("Game");
        //game.appendChild(getGameElements(doc, game, "Step"));
        //game.appendChild(getStep(doc, "1", "1", "1 1"));

        return game;
    }

    private static Node getResult(Document doc, String id, String name, String symbol) {
        Element result = doc.createElement("GameResult");

        result.appendChild(getPlayer(doc, id, name, symbol));


        /*result.setAttribute("id", id);


        result.setAttribute("name" , name);


        result.setAttribute("symbol", symbol);*/
        return result;
    }

    private static Node getDraw(Document doc, String draw) {
        Element result = doc.createElement("GameResult");
        result.setAttribute("Draw", "Draw");
        return result;
    }

    private static Node getStep(Document doc, String numberOfStep, String playerId, String location) {
        Element step = doc.createElement("Step");

        step.setAttribute("numberOfStep", numberOfStep);

        step.setAttribute("playerId", playerId);

        step.appendChild(getStepElements(doc, "Step", location));

        //step.appendChild(getStepElements(doc, step, "Step", location));
        return step;
    }




    private static Node getStepElements(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

   /* private static Node getGameElements(Document doc, Element element, String name) {
        Element node = doc.createElement(name);
        return node;
    }*/

}

