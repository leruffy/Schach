import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    static Schachbrett brett = new Schachbrett();
    static Figur figur = new Figur("sdf",'f');
    static int zugNr = 0;

    // Objekte für die Schwarzen figuren
    static König königS = new König("schwarz", (char) 9818);
    static Dame dameS = new Dame("schwarz", (char) 9819);
    static Turm turmS = new Turm("schwarz", (char) 9820);
    static Läufer läuferS = new Läufer("schwarz", (char) 9821);
    static Springer springerS = new Springer("schwarz", (char) 9822);

    static Bauer bauerS = new Bauer("schwarz", (char) 9823);

    // Objekte für die weißen figuren
    static König königW = new König("weiß", (char) 9812);
    static Dame dameW = new Dame("weiß", (char) 9813);
    static Turm turmW = new Turm("weiß", (char) 9814);
    static Läufer läuferW = new Läufer("weiß", (char) 9815);
    static Springer springerW = new Springer("weiß", (char) 9816);
    static Bauer bauerW = new Bauer("weiß", (char) 9817);

    static Figur[] blackPieces = {turmS, springerS, läuferS, dameS, königS, läuferS, springerS, turmS};
    static Figur[] whitePieces = {turmW, springerW, läuferW, dameW, königW, läuferW, springerW, turmW};

    // Stellt die Figuren in Grundstellung auf das Schachbrett
    public static void grundstellung(String[][] arr) {
        for (int zeile = 1; zeile < arr.length - 1; zeile++) {
            for (int spalte = 1; spalte < arr.length - 1; spalte++) {
                switch (zeile) {
                    case 1 -> arr[zeile][spalte] = String.valueOf(whitePieces[spalte - 1].getUnicodeFigur());
                    case 2 -> arr[zeile][spalte] = String.valueOf(bauerW.getUnicodeFigur());
                    case 7 -> arr[zeile][spalte] = String.valueOf(bauerS.getUnicodeFigur());
                    case 8 -> arr[zeile][spalte] = String.valueOf(blackPieces[spalte - 1].getUnicodeFigur());
                }
            }
        }
    }

    static void ziehen(String userName) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Spieler " + brett.getSpieler() + " bitte waehle deinen Zug");
        String zug = sc.nextLine().toUpperCase();

        // Vorübergehende funktion um die Zugregeln zu umgehen zum leichteren Testen
        if (zug.startsWith("ADMIN:")){

            int startSpalte = zug.charAt(6) - 64;
            int startZeile = 9 - Integer.parseInt(String.valueOf(zug.charAt(7)));
            int zielSpalte = zug.charAt(8) - 64;
            int zielZeile = 9 - Integer.parseInt(String.valueOf(zug.charAt(9)));

            brett.changePieces(zielZeile, zielSpalte, brett.getBrett()[startZeile][startSpalte]);
            brett.changePieces(startZeile, startSpalte, (startSpalte + startZeile) % 2 != 1 ? brett.getHell() : brett.getDunkel());
            brett.setBoardOrientation(!brett.getBoardOrientation());
            brett.darstellen(brett.getBoardOrientation());
            return;
        }
        // Aktuelles Spiel wird gespeichert und anschließend beendet
        if (zug.contains("SPEICHERN")) {
            brett.saveGame(userName);
            System.exit(0);
        }
        // Funktion zum Berechnen aller erlaubten Züge wird in Zukunft geändert, dass es automatisch bei jedem Zug angezeigt wird
        if (zug.contains("PRINT")){
            figur.getAllAllowedMoves().clear();
            for(Map.Entry<String, ArrayList<String>> entry : springerS.generateAllowedMoves(brett).entrySet()){
                figur.putInAllowedMoves(entry.getKey(),entry.getValue());
            }for(Map.Entry<String, ArrayList<String>> entry : bauerS.generateAllowedMoves(brett).entrySet()){
                figur.putInAllowedMoves(entry.getKey(),entry.getValue());
            }for(Map.Entry<String, ArrayList<String>> entry : läuferS.generateAllowedMoves(brett).entrySet()){
                figur.putInAllowedMoves(entry.getKey(),entry.getValue());
            }for(Map.Entry<String, ArrayList<String>> entry : turmS.generateAllowedMoves(brett).entrySet()){
                figur.putInAllowedMoves(entry.getKey(),entry.getValue());
            }for(Map.Entry<String, ArrayList<String>> entry : dameS.generateAllowedMoves(brett).entrySet()){
                figur.putInAllowedMoves(entry.getKey(),entry.getValue());
            }
            System.out.println(figur.getAllAllowedMoves());

        }
        boolean erlaubterZug = true;
        Pattern pattern = Pattern.compile("\\A[A-H][1-8]?[A-H]?[1-8]?"); // Regex Pattern welches nur Buchstaben von A bis H und Zahlen von 1 bis 8 erlaubt
        boolean korrekteZugangabe = pattern.matcher(zug).matches();
        if (!korrekteZugangabe) {
            System.out.println("Bitte gib einen gueltigen Zug ein");
            return; // Bricht die ziehen Funktion ab
        }
        int startSpalte = zug.charAt(0) - 64;
        int startZeile = 9 - Integer.parseInt(String.valueOf(zug.charAt(1)));
        int zielSpalte = zug.charAt(2) - 64;
        int zielZeile = 9 - Integer.parseInt(String.valueOf(zug.charAt(3)));

        char figur = brett.getBrett()[startZeile][startSpalte].charAt(0);
        if ((figur < 9818 && brett.getBoardOrientation()) || (figur > 9817 && !brett.getBoardOrientation())) {
            System.out.println("Bitte bewege deine eigene Figur");
            return;
        }
        // Zur bestimmung in welche Richtung eine Figur läuft
        int veränderungY = startZeile - zielZeile;
        int veränderungX = startSpalte - zielSpalte;

        switch (brett.getBrett()[startZeile][startSpalte].charAt(0)) {
            case 9814, 9820 -> // Unicode Türme
                    erlaubterZug = brett.isWegFrei(startZeile, startSpalte, zielZeile, zielSpalte) && turmS.isErlaubterZug(veränderungX, veränderungY);
            case 9817, 9823 -> // Unicode Bauern
                    erlaubterZug = brett.isWegFrei(startZeile, startSpalte, zielZeile, zielSpalte) && bauerS.isErlaubterZug(brett.getBrett(), startZeile, startSpalte, zielZeile, zielSpalte, veränderungX, veränderungY);
            case 9812, 9818 -> // Unicode Könige
                    erlaubterZug = brett.isWegFrei(startZeile, startSpalte, zielZeile, zielSpalte) && königS.isErlaubterZug(veränderungX, veränderungY);
            case 9816, 9822 -> // Unicode Springer
                    erlaubterZug = springerS.isErlaubterZug(veränderungX, veränderungY, zielZeile,zielSpalte, brett.getBrett());
            case 9813, 9819 -> // Unicode Damen
                    erlaubterZug = brett.isWegFrei(startZeile, startSpalte, zielZeile, zielSpalte) && dameS.isErlaubterZug(veränderungX, veränderungY);
            case 9815, 9821 -> // Unicode Läufer
                    erlaubterZug = brett.isWegFrei(startZeile, startSpalte, zielZeile, zielSpalte) && läuferS.isErlaubterZug(veränderungX, veränderungY);
        }

        if (erlaubterZug) {
            brett.changePieces(zielZeile, zielSpalte, brett.getBrett()[startZeile][startSpalte]);
            brett.changePieces(startZeile, startSpalte, (startSpalte + startZeile) % 2 != 1 ? brett.getHell() : brett.getDunkel());
            brett.setBoardOrientation(!brett.getBoardOrientation());
            brett.darstellen(brett.getBoardOrientation());
            zugNr++;
        } else {
            System.out.println("Kein erlaubter Zug");
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scStr = new Scanner(System.in);
        // Für den Fall, dass entweder ein Spiel gespeichert werden soll oder bereits ein gespeichertes Spiel vorhanden ist
        System.out.println("Bitte gib deinen Benutzernamen ein");
        String userName = scStr.nextLine().strip();
        Spieler spieler = new Spieler(userName);
        boolean loadSuccessful = true;
        if (spieler.searchUserName()) { // Schaue, ob unter dem Benutzernamen schon ein save file vorhanden ist
            // User wird gefragt, ob er ein Spiel laden will, wenn nein wird loadSuccessful auf False gesetzt
            loadSuccessful = brett.loadGame(userName);
        }else { // Kein save file vorhanden und neues Spiel wird gestartet
            brett.brettBefüllen();
            grundstellung(brett.getBrett());
            brett.darstellen(brett.getBoardOrientation());
            brett.setBrett(brett.getBrett());
        }
        if (!loadSuccessful){ // User hat das laden abgebrochen und ein neues Spiel wird gestartet
            brett.brettBefüllen();
            grundstellung(brett.getBrett());
            brett.darstellen(brett.getBoardOrientation());
            brett.setBrett(brett.getBrett());
        }
        while (zugNr < 10) {
            ziehen(userName);
        }


    }
}
