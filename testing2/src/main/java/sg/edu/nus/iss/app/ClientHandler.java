package sg.edu.nus.iss.app;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler extends Thread{
    // private DataInputStream dis;
    // private DataOutputStream dos;
    final Socket socket;
    
    // public ClientHandler(DataInputStream dis, DataOutputStream dos, Socket socket) {
    //     this.dis = dis;
    //     this.dos = dos;
    //     this.socket = socket;
    // }

    public ClientHandler(Socket socket) {
        this.socket = socket;
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
                
                message = dis.readUTF();
                System.out.println("Message from client >>> " + message);
    
                if(message.equalsIgnoreCase("exit")){
                    System.out.println("Closing this connection");
                    System.out.println("System shut down...");
                    dos.writeUTF("Server shutting down.");
                    dos.flush();
                    break;
                }

                switch(message){
                    case "1":
                        dos.writeUTF("one");
                        dos.flush();
                        break;
                    case "2":
                        dos.writeUTF("two");
                        dos.flush();
                        break;
                    case "3":
                        dos.writeUTF("three");
                        dos.flush();
                        break;
                    default:
                        dos.writeUTF("Invalid command.");
                        dos.flush();
                        break;
                }
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
