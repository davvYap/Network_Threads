package sg.edu.nus.iss.app;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.FileHandler;

public class ClientHandler extends Thread{
    // private DataInputStream dis;
    // private DataOutputStream dos;
    final Socket socket;
    private String fileName;
    List<String> cookiesList = new ArrayList<>();
    
    // public ClientHandler(DataInputStream dis, DataOutputStream dos, Socket socket) {
    //     this.dis = dis;
    //     this.dos = dos;
    //     this.socket = socket;
    // }

    public ClientHandler(Socket socket, String fileName) {
        this.socket = socket;
        this.fileName = fileName;
    }


    @Override
    public void run() {
        String message;
        while(true){
            try{
                InputStream is = socket.getInputStream();
                DataInputStream dis = new DataInputStream(new BufferedInputStream(is));
    
                OutputStream os = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(os));

                // dos.writeUTF("get-cookie || occurence || get-idioms || exit");
                // dos.flush();
                
                message = dis.readUTF();
                System.out.println("Message from client >>> " + message);
    
                if(message.equalsIgnoreCase("exit")){
                    System.out.println("Closing this connection");
                    System.out.println("System shut down...");
                    dos.writeUTF("Server shutting down.");
                    dos.flush();
                    break;

                } else if (message.equalsIgnoreCase("get-cookie")){
                    List<String> cookies = Cookie.getDataFromText(fileName);
                    String randomCookie = Cookie.randomCookie(cookies);
                    System.out.println("Random cookie get from ClientHandler >>> " + randomCookie);
                    dos.writeUTF("Random cookie >>> " + randomCookie);
                    dos.flush();
                    cookiesList.add(randomCookie);

                } else if (message.equalsIgnoreCase("occurence")){
                    Map<String,Integer> map = Cookie.calculateOccurence(cookiesList);
                    // map.forEach((k,v) -> System.out.printf("%s >>> %d\n",k,v));
                    
                    // sort the map
                    System.out.println("After sorting ****");
                    Map<String,Integer> sortedMap = Cookie.sortMap(map);
                    sortedMap.forEach((k,v) -> System.out.printf("%s >>> %d\n",k,v));

                    // convert the sorted map into a list<Entry<String,Integer>> so that can populate in file writer later
                    List<String> sortMapList = Cookie.mapToList(sortedMap);

                    // create directory and file to write results into it
                    dos.writeUTF("Enter directory name for the output file\n");
                    dos.flush();
                    String dirName = dis.readUTF();
                    
                    MyFileHandler mfh = new MyFileHandler();
                    Boolean dirCreated = mfh.createDir(dirName);
                    
                    if(dirCreated){
                        dos.writeUTF("Directory " + dirName + " created\n" + "Enter the file name \n");
                        dos.flush();
                    }else{
                        dos.writeUTF("Directory " + dirName + " already exists\n" + "Enter the file name \n");
                        dos.flush();
                    }
                    
                    String fileName = dis.readUTF();
                    try{
                        boolean fileCreated = mfh.createFile(dirName, fileName);
                        if(fileCreated){
                            dos.writeUTF("File " + fileName + " created\n" + "Data was input into the file\n");
                            dos.flush();
                        }else{
                            dos.writeUTF("File " + fileName + " already exists\n" + "Data was input into the file\n");
                            dos.flush();
                        }
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                    String path = dirName + File.separator + fileName;
                    
                    // write to file with stated path
                    mfh.writeIntoFile(path, sortMapList); 

                } else if(message.equalsIgnoreCase("get-idioms")){
                    List<String> idioms = Idioms.getPhraseFromText(fileName);
                    List<String> idiomsWord = Idioms.getWordsFromPhrase(idioms);
                    for (String phrase : idioms) {
                        dos.writeUTF(phrase);
                    }
                    dos.flush();

                } else{
                    dos.writeUTF("Invalid response.");
                    dos.flush();
                }



                // switch(message){
                //     case "1":
                //         dos.writeUTF("one");
                //         dos.flush();
                //         break;
                //     case "2":
                //         dos.writeUTF("two");
                //         dos.flush();
                //         break;
                //     case "3":
                //         dos.writeUTF("three");
                //         dos.flush();
                //         break;
                //     default:
                //         dos.writeUTF("Invalid command.");
                //         dos.flush();
                //         break;
                // }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        try{
            // dis.close();
            // dos.close();
            socket.close();
            System.err.println("Socket closed.");
            System.out.println("Waiting for client request...");
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
