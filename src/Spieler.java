import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Spieler {
    String userName;

    public Spieler(String userName) {
        this.userName = userName;
    }
    public boolean searchUserName(){
        boolean containsSaveFile = false;
        File folder = new File("./saveFiles/");
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File listOfFile : listOfFiles) {
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
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File listOfFile : listOfFiles) {
                if (listOfFile.isFile() && listOfFile.getName().contains(userName)) {
                    list.add(listOfFile.getName());
                }
            }
        }
        return list;
    }
}
