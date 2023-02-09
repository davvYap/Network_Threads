package sg.edu.nus.iss.app;

import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException, NumberFormatException{
        // get localhost IP address
        Socket socket = null;
        
        if(args[0] != null){
            while(true){
                socket = new Socket(args[0],Integer.parseInt(args[1]));
    
                InputStream is = socket.getInputStream();
                DataInputStream dis = new DataInputStream(is);
    
                OutputStream os = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);
    
                Console cons = System.console();
                String message = cons.readLine("Write to Server? 1 or 2 or 3 or exit\n");
    
                dos.writeUTF(message); // send message to Server
                dos.flush();

                String response = dis.readUTF(); // read from Server

                if(response.toLowerCase().contains("shut")){
                    System.out.println("Response from Server: "+response);
                    break;
                }else{
                    System.out.println("Response from Server: "+response);
                }
                dis.close();
                dos.close();
                is.close();
                os.close();
                socket.close();
            }
        }
    }
}
