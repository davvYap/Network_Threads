package sg.edu.nus.iss.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Idioms {
    public static List<String> getPhraseFromText(String path) throws IOException{

        BufferedReader br = new BufferedReader(new FileReader(path));

        String line = "";
        List<String> list = new ArrayList<>();

        while((line = br.readLine()) != null){
            list.add(line);
        }
        br.close();
        return list;
    }

    public static List<String> getWordsFromPhrase(List<String> list) throws IOException{

        List<String> newlist = new ArrayList<>();

        for (String line : list) {
            String[] wordsPerPhrase = line.split(" ");
            for (String word : wordsPerPhrase) {
                newlist.add(word);
            }
        }
        return newlist;
    }
}
