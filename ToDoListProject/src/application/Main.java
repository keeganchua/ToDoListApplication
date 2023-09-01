package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private ListView<TaskItem> taskListView;
    private TextField taskInputField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Create UI elements
        taskInputField = new TextField();
        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");
        Button clearCompletedButton = new Button("Clear Completed");
        taskListView = new ListView<>();
        ObservableList<TaskItem> tasks = FXCollections.observableArrayList();

        // Handle add button click
        addButton.setOnAction(e -> addTask(tasks));

        // Handle delete button click
        deleteButton.setOnAction(e -> deleteTask(tasks));
        
        // Handle clear completed button click
        clearCompletedButton.setOnAction(e -> clearCompletedTasks(tasks));
        // Create layout
        VBox root = new VBox(10); // VBox with spacing
        HBox buttonBox = new HBox(10); // HBox for buttons

        buttonBox.getChildren().addAll(addButton, deleteButton, clearCompletedButton);
        root.getChildren().addAll(taskInputField, buttonBox, taskListView);

        // Set up the scene and stage
        Scene scene = new Scene(root, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("To-Do List App");
        primaryStage.show();

        // Set the items of the ListView
        taskListView.setItems(tasks);
        taskListView.setCellFactory(param -> new ListCell<TaskItem>() {
            @Override
            protected void updateItem(TaskItem task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    CheckBox checkBox = new CheckBox(task.getName());
                    checkBox.setSelected(task.isCompleted());
                    checkBox.setOnAction(event -> task.setCompleted(checkBox.isSelected()));
                    setGraphic(checkBox);
                }
            }
        });
    }

    private void addTask(ObservableList<TaskItem> tasks) {
        String taskName = taskInputField.getText().trim();
        if (!taskName.isEmpty()) {
            tasks.add(new TaskItem(taskName));
            taskInputField.clear();
        }
    }

    private void deleteTask(ObservableList<TaskItem> tasks) {
        int selectedIndex = taskListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            tasks.remove(selectedIndex);
        }
    }
    
    private void clearCompletedTasks(ObservableList<TaskItem> tasks) {
        tasks.removeIf(TaskItem::isCompleted);
    }
}
