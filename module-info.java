module DemoGame {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.logging;
	requires javafx.graphics;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
}
