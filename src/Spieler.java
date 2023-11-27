import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Spieler {
    String userName;

    public Spieler(String userName) {
        this.userName = userName;
    }
    // Schaue ob, der aktuelle User bereits ein Spiel gespeichert hat
    public boolean searchUserName(){
        boolean containsSaveFile = false;
        File folder = new File("./saveFiles/");
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File listOfFile : listOfFiles) {
                // Ein save file zu dem User wurde gefunden
                if (listOfFile.isFile() && listOfFile.getName().contains(userName)) {
                    containsSaveFile = true;
                }
            }
        }
        return containsSaveFile;
    }

    public List<String> listFiles(){
        List<String> list = new ArrayList<>();
        File folder = new File("./saveFiles/");
        // File Array mit allen save files
        File[] listOfFiles = folder.listFiles();
        // Zur nummerierung der save files bei der ausgabe damit der User eine Nummer zum Auswählen stehen hat
        int z = 1;
        if (listOfFiles != null) {
            // Iteriere durch alle Dateien und füge alle zu dem User passenden Dateien in die Liste und gebe sie nummeriert auf die Konsole aus
            for (File listOfFile : listOfFiles) {
                if (listOfFile.isFile() && listOfFile.getName().contains(userName)) {
                    list.add(listOfFile.getName());
                    System.out.println(z + " " + listOfFile.getName());
                    z++;
                }
            }
        }
        return list;
    }
}
