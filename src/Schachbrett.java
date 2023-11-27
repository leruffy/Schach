import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Schachbrett {
    private String[][] brett = new String[10][10];
    private boolean boardOrientation = true; // True wenn weiß am Zug ist
    // Für die leeren felder des Schachbrettes der Unicode 8202 ist ein Whitespace char zur passenden formatierung bei der Ausgabe auf die Konsole
    private final String hell = ((char) 9724 + "" + (char) (8202));
    private final String dunkel = ((char) 9723 + "" + (char) (8202));

    public String[][] getBrett() {
        return brett;
    }

    public void setBrett(String[][] brett) {
        this.brett = brett;
    }

    public boolean getBoardOrientation() {
        return boardOrientation;
    }

    public void setBoardOrientation(boolean boardOrientation) {
        this.boardOrientation = boardOrientation;
    }

    public String getHell() {
        return hell;
    }


    public String getDunkel() {
        return dunkel;
    }

    public void brettBefüllen() {
        for (int zeile = 0; zeile < brett.length; zeile++) {
            for (int spalte = 0; spalte < brett[zeile].length; spalte++) {
                switch (zeile) {
                    case 0, 9 ->
                            brett[zeile][spalte] = (spalte >= 1 && spalte <= 8) ? String.valueOf((char) (spalte + 64)) + (char) (8200) + (char) (8202) : String.valueOf((char) (32));
                    default -> {
                        if (spalte == 0 || spalte == 9) {
                            brett[zeile][spalte] = String.valueOf((char) (57 - zeile));
                        } else {
                            brett[zeile][spalte] = (zeile + spalte) % 2 != 1 ? hell : dunkel;
                        }
                    }
                }
            }
        }
    }

    public void changePieces(int zeile, int spalte, String piece) {
        brett[zeile][spalte] = piece;
    }

    // Gibt das Brett passend für den jeweiligen Spieler der am Zug ist, auf die Konsole aus
    public void darstellen(boolean richtung) {
        for (int zeile = 0; zeile < brett.length; zeile++) {
            for (int spalte = 0; spalte < brett.length; spalte++) {
                int i = (richtung) ? zeile : 9 - zeile;
                int j = (richtung) ? spalte : 9 - spalte;
                System.out.print(brett[i][j]);
            }
            System.out.println();
        }
    }

    public String getSpieler(){
        return (getBoardOrientation()) ? "Weiß" : "Schwarz";
    }

    public boolean isWegFrei(int startZ, int startS, int zielZ, int zielS) {
        boolean wegFrei = true;
        // Zielangabe nicht auf dem Brett
        if (zielZ<1 ||zielZ > 8 || zielS<1 ||zielS > 8){
            return false;
        }
        try {
            int x = startZ;
            int y = startS;
            // Figur führt einen Diagonalen Zug aus
            if (startZ != zielZ && startS != zielS) {
                while (x != zielZ && y != zielS) {
                    x = (startZ >= zielZ) ? x - 1 : x + 1;
                    y = (startS >= zielS) ? y - 1 : y + 1;
                    if (brett[startZ][startS].charAt(0) >= 9818 && !(brett[x][y].equals(hell)) && !(brett[x][y].equals(dunkel))) { // Schwarze Figur wird bewegt
                        // Spieler schwarz versucht seine Figur auf ein Feld zu setzen auf dem schon eine Schwarze Figur steht
                        if (brett[x][y].charAt(0) >= 9818) {
                            wegFrei = false;
                            // Spieler schwarz versucht eine Weiße Figur zu überspringen
                        } else if (x != zielZ && y != zielS) {
                            wegFrei = false;
                        }
                    } else if (brett[startZ][startS].charAt(0) <= 9817 && !(brett[startZ][startS].equals(hell)) && !(brett[x][y].equals(dunkel))) { // Weiße Figur wird bewegt
                        // Spieler weiß versucht seine Figur auf ein Feld zu setzen auf dem schon eine Weiße Figur steht
                        if (brett[x][y].charAt(0) <= 9817) {
                            wegFrei = false;
                            // Spieler weiß versucht eine Schwarze Figur zu überspringen
                        } else if (x != zielZ && y != zielS) {
                            wegFrei = false;
                        }
                    }
                }
                // Horizontaler/Vertikaler Zug
            } else {
                while (x != zielZ || y != zielS) {
                    if (x > zielZ) {
                        x--;
                    } else if (x < zielZ && x != 0) {
                        x++;
                    }
                    if (y > zielS) {
                        y--;
                    } else if (y < zielS && y != 0) {
                        y++;
                    }
                    // startZ und startS stehen für die Figur die bewegt wird und x sowie y stehen für das Feld auf dem Schachbrett welches gerade geprüft wird
                    if (brett[startZ][startS].charAt(0) >= 9818 && !(brett[x][y].equals(hell)) && !(brett[x][y].equals(dunkel))) { // Schwarze Figur wird bewegt
                        // Spieler schwarz versucht seine Figur auf ein Feld zu setzen auf dem schon eine Schwarze Figur steht
                        if (brett[x][y].charAt(0) >= 9818) {
                            wegFrei = false;
                            // Spieler schwarz versucht eine Weiße Figur zu überspringen
                        } else if (x != zielZ || y != zielS) {
                            wegFrei = false;
                        }
                    } else if (brett[startZ][startS].charAt(0) <= 9817 && !(brett[x][y].equals(hell)) && !(brett[x][y].equals(dunkel))) { // Weiße Figur wird bewegt
                        // Spieler weiß versucht seine Figur auf ein Feld zu setzen auf dem schon eine Weiße Figur steht
                        if (brett[x][y].charAt(0) <= 9817) {
                            wegFrei = false;
                            // Spieler weiß versucht eine Schwarze Figur zu überspringen
                        } else if (x != zielZ || y != zielS) {
                            wegFrei = false;
                        }
                    }
                }
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return wegFrei;
    }

    public void saveGame(String userName) throws IOException {
        // Zum Hinzufügen des aktuellen Zeitstempels zum Benutzernamen
        long time = System.currentTimeMillis();
        FileWriter writer = new FileWriter("./saveFiles/" + userName + time + ".txt");
        PrintWriter printWriter = new PrintWriter(writer);
        for (int spalte = 1; spalte < brett.length - 1; spalte++) {
            for (int zeile = 1; zeile < brett.length - 1; zeile++) {
                // Schreibt den Inhalt von jedem Feld in eine neue Zeile einer Textdatei
                printWriter.append(brett[spalte][zeile]).append("\n");
            }
        }
        writer.close();
        printWriter.close();
    }

    public boolean loadGame(String userName) throws IOException {
        // Wird auf False gesetzt, falls der User das Laden mittendrin abbricht, damit in der main Methode dann ein neues Spiel gestartet wird
        boolean loadSuccessful = true;
        List<String> saveFiles = new ArrayList<>();
        Scanner scInt = new Scanner(System.in);
        Scanner scStr = new Scanner(System.in);
        System.out.println("Es sind bereits Spielstände mit deinem Benutzernamen vorhanden möchtest du einen davon laden?");
        String ladenJaNein = scStr.nextLine();
        // Benutzer möchte altes Spiel laden
        if (ladenJaNein.equalsIgnoreCase("ja")) {
            Spieler spieler = new Spieler(userName);
            saveFiles = spieler.listFiles();

            System.out.println("Bitte wähle die Nummer des zu ladenden Spielstandes oder 0 um doch ein neues Spiel zu starten");
            int saveFileToLoad = scInt.nextInt();
            if (saveFileToLoad != 0) {
                // Brett wird mit leeren Feldern befüllt
                brettBefüllen();
                // Zum Auslesen der gewählten Datei
                BufferedReader br = new BufferedReader(new FileReader("./saveFiles/" + saveFiles.get(saveFileToLoad - 1)));
                for (int i = 1; i < brett.length - 1; i++) {
                    for (int j = 1; j < brett.length - 1; j++) {
                        String line = br.readLine();
                        // Zeile entspricht einer Figur
                        if (line.charAt(0) >= 9812 && line.charAt(0) <= 9823) {
                            brett[i][j] = String.valueOf(line.charAt(0));
                        } else {
                            brett[i][j] = (i + j) % 2 != 1 ? hell : dunkel;
                        }
                    }
                }
                darstellen(boardOrientation);
            } else {
                loadSuccessful = false;
            }
        }else {
            loadSuccessful = false;
        }
        return loadSuccessful;
    }

}
