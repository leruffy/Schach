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
        setAllAllowedMoves(allAllowedMoves);
        allAllowedMoves = getAllAllowedMoves();
        ArrayList<String> allowedMoves = new ArrayList<>();

        for (int zeile = 1; zeile< arr.length;zeile++){
            for (int spalte = 1; spalte < arr.length;spalte++){
                if (arr[zeile][spalte].charAt(0) == 9823 || arr[zeile][spalte].charAt(0) == 9817){
                    for (int x = 1; x< arr.length;x++){
                        for (int y = 1;y< arr.length;y++){
                            if (isErlaubterZug(arr,zeile,spalte,x,y,spalte-y,zeile-x) && brett.isWegFrei(zeile,spalte,x,y)){
                                allowedMoves.add(String.valueOf((char) (64 + y)) + (char) (57 - x));
                            }
                        }
                    }
                    if (allowedMoves.size() != 0) {
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
        if (arr[zielZeile][zielSpalte].equals(brett.getHell()) && verX != 0){
            erlaubterZug = false;
        } else if (arr[zielZeile][zielSpalte].equals(brett.getDunkel()) && verX != 0) {
            erlaubterZug = false;
        } else if (verY > 2 || verY < -2) {
            erlaubterZug = false;
        } else if (arr[startZeile][startSpalte].charAt(0) == 9823 && verY < 1) {
            erlaubterZug = false;
        } else if (arr[startZeile][startSpalte].charAt(0) == 9817 && verY > -1) {
            erlaubterZug = false;
        } else if (startZeile != 2 && verY == -2) {
            erlaubterZug = false;
        } else if (startZeile != 7 && verY == 2) {
            erlaubterZug = false;
        } else if (verX > 1 || verX < -1) {
            erlaubterZug = false;
        } else if (!arr[zielZeile][zielSpalte].equals(brett.getHell()) && !arr[zielZeile][zielSpalte].equals(brett.getDunkel()) && verX == 0) {
            erlaubterZug = false;
        }
        return erlaubterZug;
    }
}
