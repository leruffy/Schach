import java.util.ArrayList;
import java.util.HashMap;

public class Springer extends Figur{
    public Springer(String farbe, char unicodeFigur) {
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
                if (arr[zeile][spalte].charAt(0) == 9822 || arr[zeile][spalte].charAt(0) == 9816){
                    for (int x = 1; x< arr.length-1;x++){
                        for (int y = 1;y< arr.length-1;y++){
                            if (isErlaubterZug(spalte-y,zeile-x, x,y,arr)){
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

    public boolean isErlaubterZug(int verX, int verY, int zielZ, int zielS, String[][] arr) {
        boolean erlaubterZug = true;
        if (Math.abs(verX) != 2 && Math.abs(verX) != 1){
            erlaubterZug = false;
        } else if (Math.abs(verY) != 2 && Math.abs(verY) != 1) {
            erlaubterZug = false;
        } else if (Math.abs(verX) == Math.abs(verY)) {
            erlaubterZug = false;
        } else if (arr[verY+zielZ][verX+zielS].charAt(0) >= 9818 && arr[zielZ][zielS].charAt(0) >= 9818) {
            erlaubterZug = false;
        } else if (arr[verY + zielZ][verX + zielS].charAt(0) <= 9817 && arr[zielZ][zielS].charAt(0) <= 9817 && arr[zielZ][zielS].charAt(0) > 9800) {
            erlaubterZug = false;
        }
        return erlaubterZug;
    }
}
