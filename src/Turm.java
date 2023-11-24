import java.util.ArrayList;
import java.util.HashMap;

public class Turm extends Figur{

    public Turm(String farbe, char unicodeFigur) {
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
                if (arr[zeile][spalte].charAt(0) == 9820 || arr[zeile][spalte].charAt(0) == 9814){
                    for (int x = 1; x< arr.length;x++){
                        for (int y = 1;y< arr.length;y++){
                            if (isErlaubterZug(spalte-y,zeile-x) && brett.isWegFrei(zeile,spalte,x,y)){
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
    public boolean isErlaubterZug(int verX, int verY){
        return verX == 0 ^ verY == 0;
    }
}
