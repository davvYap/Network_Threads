package sg.edu.nus.iss.app;

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

    public static void main(String[] args) throws IOException, ClassNotFoundException{
        server = new ServerSocket(port);

        // keep listen until receive "exit" from client side
        while(true){
            System.out.println("Waiting for client request...");

            //create a socket and wait for client connection
            Socket socket = server.accept();

            // create InputStream& DataInputStream to listen client request
            InputStream is = socket.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            // convert to string 
            String message = dis.readUTF();
            System.out.printf("Message from client: %s\n",message);

            // create OutputStream & DataOutputStream and write to client
            OutputStream os = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);

            if(message.equals("exit")){
                dos.writeUTF(">>> Server shut down");
                break;
            }

            switch(message){
                case "1":
                    dos.writeUTF("one");
                    break;
                case "2":
                    dos.writeUTF("two");
                    break;
                case "3":
                    dos.writeUTF("three");
                default:
                    dos.writeUTF("Invalid command.");
            }

            dis.close();
            dos.close();
            is.close();
            os.close();
        }

        System.out.println(">>> Server shutting down");
        server.close();
    }
}
