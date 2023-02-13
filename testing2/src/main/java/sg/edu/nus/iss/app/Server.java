package sg.edu.nus.iss.app;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static ServerSocket server;
    private static int port = 3999;

    public static void main(String[] args) throws IOException{
        server = new ServerSocket(port);
        System.out.println("Waiting for client request...");
        
        while(true){
            try{
                Socket socket = server.accept();
                System.out.println("A new client connected: " + socket);

                InputStream is = socket.getInputStream();
                DataInputStream dis = new DataInputStream(new BufferedInputStream(is));
    
                OutputStream os = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(os));

                // String message = dis.readUTF();
                // System.out.println("Message from client >>> " + message);
    
                // if(message.equalsIgnoreCase("exit")){
                //     System.out.println("Closing this connection");
                //     System.out.println("System shut down...");
                //     dos.writeUTF("Server shutting down.");
                //     dos.flush();
                    
                //     break;
                // }

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

                // ClientHandler cch = new ClientHandler(dis, dos, socket);
                ClientHandler cch = new ClientHandler(socket);
                cch.start();
                
            }catch(IOException e){
                e.printStackTrace();
                server.close();
                System.exit(0);
            }
        }

    }
}
