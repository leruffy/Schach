import java.util.ArrayList;
import java.util.HashMap;

public class Figur {
    public String farbe;
    public char unicodeFigur;
    public HashMap<String, ArrayList<String>> allAllowedMoves = new HashMap<>();

    public HashMap<String, ArrayList<String>> getAllAllowedMoves() {
        return allAllowedMoves;
    }

    public void setAllAllowedMoves(HashMap<String, ArrayList<String>> allAllowedMoves) {
        this.allAllowedMoves = allAllowedMoves;
    }
    public void putInAllowedMoves(String key, ArrayList<String> value){
        allAllowedMoves.put(key,value);
    }

    public Figur(String farbe, char unicodeFigur) {
        this.farbe = farbe;
        this.unicodeFigur = unicodeFigur;
    }

    public String getFarbe() {
        return farbe;
    }

    public void setFarbe(String farbe) {
        this.farbe = farbe;
    }

    public char getUnicodeFigur() {
        return unicodeFigur;
    }

    public void setUnicodeFigur(char unicodeFigur) {
        this.unicodeFigur = unicodeFigur;
    }
}
