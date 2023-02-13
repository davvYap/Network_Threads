package sg.edu.nus.iss.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MyFileHandler {
    
    public Boolean createDir(String dirName){

        File directory = new File(dirName);
        return directory.mkdir();   // will return boolean value once created directory succesffuly
    }

    public Boolean createFile(String dirName, String fileName) throws IOException{

        File newFile = new File(dirName + File.separator + fileName);
        return newFile.createNewFile();
    }

    public void listDirFiles(String dirName) throws IOException{

        File directory = new File(dirName);
        if (directory.exists()){
            File[] fileList = directory.listFiles();
            
            for (File file : fileList) {
                System.out.println("File path >>> " + file.getAbsolutePath());
            }

        }else{
            System.out.println("Directory " + dirName + " does not exist.");
        }
    }

    public void writeIntoFile(String path, List<String> list) throws IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter(path));

        for (String string : list) {
            bw.write(string);
        }
        bw.close();
    }
}
