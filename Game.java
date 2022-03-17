package theGame;
import java.io.*;
import java.util.Scanner;



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



public class Game {
    static int VERTICAL = 3;
    static int HORIZONTAL = 3;
    public static boolean repeat = true;
    public static int firstCount = 0;
    public static int secondCount = 0;
    public static int step = 1;
    public static String firstPlayerName;
    public static String secondPlayerName;
    public static String location;

    static char [][] gameField = new char [VERTICAL][HORIZONTAL];

    private static Scanner scanner = new Scanner(System.in);
    
    static File file = new File("src/test.xml");


   public static char firstPlayerSign = 'X';
   public static char secondPlayerSign = 'O';
   public static char initialSign = '.';


    //начальная настройка игрового поля
    public static void initialPlayField() {
        for (int i = 0; i < VERTICAL; i++) {
            for (int j = 0; j < HORIZONTAL; j++) {
                gameField[i][j] = initialSign;
            }
        }
    }

    //Графическое отображение игрового поля
    public static void printField() {
        System.out.println("  1 2 3");
        int count = 1;
        for (int i = 0; i < VERTICAL; i++) {
            for (int j = 0; j < HORIZONTAL; j++) {
                if (j == 0) {
                    System.out.print(count + "|");
                    count++;
                }
                System.out.print(gameField[i][j] + "|");
            }
            System.out.println();
        }
    }

    // запись символа на игровое поле
    public static void setSym(int y, int x, char sym) {
        gameField[y][x] = sym;
    }

    public static void firstPlayerStep() {
        int x;
        int y;

        do {
            System.out.println("Введите координаты: X Y (1-3)");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        } while (!isCellValid(y,x));
        location = x + " " + y;
        setSym(y,x, firstPlayerSign);

    }

    public static void secondPlayerStep() {
        int x;
        int y;

        do {
            System.out.println("Введите координаты: X Y (1-3)");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        } while (!isCellValid(y,x));
        location = (x + 1) + " " + (y + 1);
        setSym(y,x, secondPlayerSign);
    }

    // Проверка правильности координат
    public static boolean isCellValid( int y, int x) {
        if (x < 0 || y < 0 || x > HORIZONTAL - 1 || y > VERTICAL - 1) {
            return false;
        }
        return gameField[y][x] == initialSign;
    }

    // проверка, есть ли свободные места на игровом поле
    public static boolean isFieldFull() {
        for (int i = 0; i < VERTICAL; i++) {
            for (int j = 0; j < HORIZONTAL; j++) {
                if (gameField[i][j] == initialSign) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean checkWin(char sym) {
        // Проверка горизонтальных позиций на наличие трех подряд ячеек
        if (gameField[0][0] == sym && gameField[0][1] == sym && gameField[0][2] == sym) {
            return true;
        }
        if (gameField[1][0] == sym && gameField[1][1] == sym && gameField[1][2] == sym) {
            return true;
        }
        if (gameField[2][0] == sym && gameField[2][1] == sym && gameField[2][2] == sym) {
            return true;
        }


        // Проверка вертикальных позиций на наличие трех подряд ячеек

        if (gameField[0][0] == sym && gameField[1][0] == sym && gameField[2][0] == sym) {
            return true;
        }
        if (gameField[0][1] == sym && gameField[1][1] == sym && gameField [2][1] == sym) {
            return true;
        }
        if (gameField[0][2] == sym && gameField[1][2] == sym && gameField[2][2] == sym) {
            return true;
        }


        //Проверка диагоналей на наличие трех ячеек подряд

        if (gameField[0][0] == sym && gameField[1][1] == sym && gameField[2][2] == sym) {
            return true;
        }

        return false;
    }


    public static void main(String[] args) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        Document doc = factory.newDocumentBuilder().newDocument();

        Element root = doc.createElement("Gameplay");
        doc.appendChild(root);



        System.out.println("Введите имя первого игрока:");
        firstPlayerName = scanner.nextLine();
        Player one = new Player(1, firstPlayerName, firstPlayerSign);
        System.out.println("Введите имя второго игрока:");
        secondPlayerName = scanner.nextLine();
        Player two = new Player(2, secondPlayerName, secondPlayerSign);
        

        root.appendChild(ParserXml.getPlayer(doc, one.getId() + "", one.getName(), one.getSymbol() + ""));
        root.appendChild(ParserXml.getPlayer(doc, two.getId() + "", two.getName(), two.getSymbol() + ""));


        Element game = doc.createElement("Game");
        root.appendChild(game);
        while (repeat) {
            initialPlayField();
            printField();

            while (true) {
                firstPlayerStep();
                one.setLocation(location);
                System.out.println("Step " + "num= " + Player.step + " playerId= " + one.getId() + " делает ход в локацию " + one.getLocation());
                game.appendChild(ParserXml.getStep(doc, Player.step + "", one.getId() + "", one.getLocation()));
                printField();
                if (checkWin(firstPlayerSign)) {
                    root.appendChild(ParserXml.getResult(doc,one.getId() + "", one.getName(), one.getSymbol() + "" ));
                    System.out.println(firstPlayerName + " победил(a)!");
                    firstCount +=3 ;
                    break;
                }
                if (isFieldFull()) {
                    System.out.println("Ничья!");
                    root.appendChild(ParserXml.getDraw(doc));
                    firstCount++;
                    break;
                }
                Player.step++;

                secondPlayerStep();
                two.setLocation(location);
                System.out.println("Step " + "num= " + Player.step + " playerId= " + two.getId() + " делает ход в локацию " + two.getLocation());
                game.appendChild(ParserXml.getStep(doc, Player.step + "", two.getId() + "", two.getLocation()));
                printField();
                if (checkWin(secondPlayerSign)) {
                    root.appendChild(ParserXml.getResult(doc,two.getId() + "", two.getName(), two.getSymbol() + "" ));
                    System.out.println(secondPlayerName + " победил(a)!");
                    secondCount += 3;
                    break;
                }
                if (isFieldFull()) {
                    System.out.println("Ничья!");
                    root.appendChild(ParserXml.getDraw(doc));
                    secondCount++;
                    break;
                }
                Player.step++;
            }
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(file));
            
            System.out.println("Хотите сыграть еще?");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String answer = reader.readLine();
            if (answer.equals("No")) {
                repeat = false;
            }
            Player.step = 1;
        }     
    }
}
