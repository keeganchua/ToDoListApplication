package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private ListView<String> taskListView;
    private TextField taskInputField;

    @Override
    public void start(Stage primaryStage) {
        // Create UI elements
        taskInputField = new TextField();
        Button addButton = new Button("Add");
        taskListView = new ListView<>(FXCollections.observableArrayList());

        // Handle add button click
        addButton.setOnAction(e -> addTask());

        // Create layout
        VBox root = new VBox(10); // VBox with spacing
        root.getChildren().addAll(taskInputField, addButton, taskListView);

        // Set up the scene and stage
        Scene scene = new Scene(root, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("To-Do List App");
        primaryStage.show();
    }

    private void addTask() {
        String taskName = taskInputField.getText();
        if (!taskName.isEmpty()) {
            taskListView.getItems().add(taskName);
            taskInputField.clear();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
