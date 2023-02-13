package sg.edu.nus.iss.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Random;
import java.util.Set;

public class Cookie {

    public static List<String> getDataFromText(String path) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(path));

        String line = "";
        List<String> list = new ArrayList<>();

        while((line = br.readLine()) != null){
            list.add(line);
        }
        return list;
    }

    public static String randomCookie(List<String> list){
        Random rand = new Random();
        int randNum = rand.nextInt(list.size());

        return list.get(randNum);
    }

    public static Map<String,Integer> calculateOccurence(List<String> cookies){
        Map<String,Integer> map = new HashMap<>();

        for (String cookie : cookies) {
            if(map.containsKey(cookie)){
                map.put(cookie, map.get(cookie)+1);
            }else{
                map.put(cookie, 1);
            }
        }
        return map;
    }

    public static void printMap(Map<String,Integer> map){
        System.out.println(map);
    }

    public static Map<String,Integer> sortMap(Map<String,Integer> map){
        Set<Entry<String,Integer>> set = map.entrySet();
        List<Entry<String,Integer>> setList = new ArrayList<>(set);

        setList.sort(Entry.comparingByValue());

        Map<String,Integer> newMap = new LinkedHashMap<>();
        setList.forEach(t -> newMap.put(t.getKey(), t.getValue()));

        return newMap;
    }

    public static List<String> mapToList(Map<String,Integer> map){
        Set<Entry<String,Integer>> set = map.entrySet();
        List<Entry<String,Integer>> setList = new ArrayList<>(set);
        List<String> newList = new ArrayList<>();
        for (Entry<String,Integer> entry: setList) {
            newList.add(entry.getKey() + " >>> " + entry.getValue() + "\n");
        }
        return newList;
    }
}
