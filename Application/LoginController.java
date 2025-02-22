package application;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void loginAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Create a new thread for the login operation
        Thread loginThread = new Thread(() -> {
            boolean isValidLogin = performLogin(username, password);

            // Use Platform.runLater to update the UI from a non-JavaFX thread
            javafx.application.Platform.runLater(() -> {
                if (isValidLogin) {
                    if ("admin".equals(username)) {
                        System.out.println("Admin login successful!");
                        // Handle admin login actions
                    } else {
                        System.out.println("User login successful!"+username);
                        
						try {
							Parent root;
							FXMLLoader loader=new FXMLLoader(getClass().getResource("GameZoneMain.fxml"));
							//root = FXMLLoader.load(getClass().getResource("GameZoneMain.fxml"));
							root=loader.load();
							
							GameZoneMainController controller=loader.getController();
							controller.displayname(username);
							Scene scene = new Scene(root);
							 Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
							window.setTitle("GameZone");
							window.getIcons().add(new Image("gamepad.gif"));
							window.setResizable(false);
							
		                        
		                        window.setScene(scene);
		                        window.show();
						} catch (IOException e) {
							 
							e.printStackTrace();
							
						}
                        
                       
                    }
                    // Navigate to the main application or perform other actions
                } else {
                    System.out.println("Invalid login credentials!");
                }
            });
        });

        // Start the thread
        loginThread.start();
    }

    @FXML
    private void registerAction() {
        mainApp.showRegistrationPage();
    }

    private boolean performLogin(String username, String password) {
        if ("admin".equals(username) && "admin".equals(password)) {
            // Admin login successful
            return true;
        } else {
            // Implement your logic to check against the server or file
            try (Socket socket = new Socket("localhost", 1234);
                 PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

                // Send login request to the server
                writer.println("LOGIN:" + username + ":" + password);

                // Read the response from the server
                String response = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();

                return response.equals("SUCCESS");

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

}
