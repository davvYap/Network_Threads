package sg.edu.nus.iss.app;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    private static int port = 3999;

    public static void main(String[] args) throws UnknownHostException, IOException{
        Socket socket = null;

        if(args.length > 0){
            port = Integer.parseInt(args[0]);
        }

        socket = new Socket("localhost",port);

        DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println("Get cookies?");
            String message = scanner.nextLine();
            dos.writeUTF(message);
            dos.flush();

            String response = dis.readUTF();

            System.out.println("Response from server >>> " + response);

            if(response.toLowerCase().contains("shut")){
                break;
            }
        }
        dis.close();
        dos.close();
        socket.close();
    }
}
