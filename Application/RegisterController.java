package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void registerAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Create a new thread for the registration operation
        Thread registerThread = new Thread(() -> {
            boolean isRegistrationSuccessful = performRegistration(username, password);

            // Use Platform.runLater to update the UI from a non-JavaFX thread
            javafx.application.Platform.runLater(() -> {
                if (isRegistrationSuccessful) {
                    System.out.println("Registration successful!");
                    // Navigate back to the login page
                    mainApp.showLoginPage();
                } else {
                    System.out.println("Invalid registration details!");
                }
            });
        });

        // Start the thread
        registerThread.start();
    }
    @FXML
    void onlogIn(ActionEvent event) throws IOException {
    	 FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
         Parent root = loader.load();
         Scene scene = new Scene(root);
         Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
         window.setScene(scene);
         window.show();
    }


    private boolean performRegistration(String username, String password) {
        try (Socket socket = new Socket("localhost", 1234);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            // Send registration request to the server
            writer.println("REGISTER:" + username + ":" + password);

            // Read the response from the server
            String response = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();

            return response.equals("SUCCESS");

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
