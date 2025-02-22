package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String request = reader.readLine();
            String[] parts = request.split(":");
            String response;

            if (parts.length == 3) {
                String action = parts[0];
                String username = parts[1];
                String password = parts[2];

                if (action.equals("LOGIN")) {
                    response = FileManager.isValidLogin(username, password) ? "SUCCESS" : "FAILURE";
                } else if (action.equals("REGISTER")) {
                    response = FileManager.createUser(username, password) ? "SUCCESS" : "FAILURE";
                } else {
                    response = "INVALID_ACTION";
                }
            } else {
                response = "INVALID_REQUEST";
            }

            writer.println(response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


