public class König extends Figur{
    public König(String farbe, char unicodeFigur) {
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

    public boolean isErlaubterZug(int verX, int verY){
        return verX >= -1 && verX <= 1 && verY >= -1 && verY <= 1;
    }
}
