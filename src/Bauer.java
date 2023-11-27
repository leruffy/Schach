import java.util.ArrayList;
import java.util.HashMap;

public class Bauer extends Figur {
    Schachbrett brett = new Schachbrett();
    public Bauer(String farbe, char unicodeFigur) {
        super(farbe, unicodeFigur);
    }

    @Override
    public String getFarbe() {
        return super.getFarbe();
    }

    @Override
    public void setFarbe(String farbe) {
        super.setFarbe(farbe);
    }

    @Override
    public char getUnicodeFigur() {
        return super.getUnicodeFigur();
    }

    @Override
    public void setUnicodeFigur(char unicodeFigur) {
        super.setUnicodeFigur(unicodeFigur);
    }

    @Override
    public HashMap<String, ArrayList<String>> getAllAllowedMoves() {
        return super.getAllAllowedMoves();
    }

    @Override
    public void setAllAllowedMoves(HashMap<String, ArrayList<String>> allAllowedMoves) {
        super.setAllAllowedMoves(allAllowedMoves);
    }

    public HashMap<String,ArrayList<String>> generateAllowedMoves(Schachbrett brett){
        String[][] arr = brett.getBrett();
        HashMap<String,ArrayList<String>> allAllowedMoves = new HashMap<>();
        // Die Hashmap der Figur klasse wird zu Beginn leer gemacht
        setAllAllowedMoves(allAllowedMoves);
        allAllowedMoves = getAllAllowedMoves();
        ArrayList<String> allowedMoves = new ArrayList<>();

        // Iteriere durch das Schachbrett und suche nach einem Bauern
        for (int zeile = 1; zeile< arr.length;zeile++){
            for (int spalte = 1; spalte < arr.length;spalte++){
                // Bauer gefunden
                if (arr[zeile][spalte].charAt(0) == 9823 || arr[zeile][spalte].charAt(0) == 9817){
                    // Iteriere durch das Schachbrett und überprüfe, ob die aktuelle Position ein erlaubter zug für den aktuellen Bauer ist
                    for (int x = 1; x< arr.length;x++){
                        for (int y = 1;y< arr.length;y++){
                            if (isErlaubterZug(arr,zeile,spalte,x,y,spalte-y,zeile-x) && brett.isWegFrei(zeile,spalte,x,y)){
                                // Fügt die Zielkoordinate in eine Arraylist da sie erlaubt ist
                                allowedMoves.add(String.valueOf((char) (64 + y)) + (char) (57 - x));
                            }
                        }
                    }
                    // Damit keine Figuren in die Hashmap kommen die sowieso keinen erlaubten zug haben
                    if (allowedMoves.size() != 0) {
                        // Fügt die aktuelle Position des Bauern als Key und die Arraylist mit allen erlaubten zügen als Value in die Hashmap
                        allAllowedMoves.put(String.valueOf((char) (64 + spalte)) + (char) (57 - zeile), allowedMoves);
                    }
                    allowedMoves = new ArrayList<>();
                }
            }
        }

        setAllAllowedMoves(allAllowedMoves);
        return allAllowedMoves;
    }

    public boolean isErlaubterZug(String[][] arr, int startZeile, int startSpalte, int zielZeile, int zielSpalte, int verX, int verY){
        boolean erlaubterZug = true;
        // Bauer versucht Diagonal zu einem Leeren Feld zu laufen
        if ((arr[zielZeile][zielSpalte].equals(brett.getHell()) || arr[zielZeile][zielSpalte].equals(brett.getDunkel())) && verX != 0){
            erlaubterZug = false;
        }
        // Bauer versucht mehr als 2 Felder zu laufen
        else if (verY > 2 || verY < -2) {
            erlaubterZug = false;
        }
        // Schwarzer Bauer versucht rückwärts zu laufen
        else if (arr[startZeile][startSpalte].charAt(0) == 9823 && verY < 1) {
            erlaubterZug = false;
        }
        // Weißer Bauer versucht rückwärts zu laufen
        else if (arr[startZeile][startSpalte].charAt(0) == 9817 && verY > -1) {
            erlaubterZug = false;
        }
        // Schwarzer Bauer versucht außerhalb seiner Startposition 2 Felder zu laufen
        else if (startZeile != 2 && verY == -2) {
            erlaubterZug = false;
        }
        // Weißer Bauer versucht außerhalb seiner Startposition 2 Felder zu laufen
        else if (startZeile != 7 && verY == 2) {
            erlaubterZug = false;
        }
        // Bauer versucht mehr als 2 Felder Diagonal zu laufen
        else if (verX > 1 || verX < -1) {
            erlaubterZug = false;
        }
        // Bauer versucht Horizontal eine Figur rauszuschmeißen
        else if (!arr[zielZeile][zielSpalte].equals(brett.getHell()) && !arr[zielZeile][zielSpalte].equals(brett.getDunkel()) && verX == 0) {
            erlaubterZug = false;
        }
        return erlaubterZug;
    }
}
