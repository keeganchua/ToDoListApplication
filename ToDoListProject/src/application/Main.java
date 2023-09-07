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

    private ObservableList<TaskItem> tasks = FXCollections.observableArrayList();
    private TaskDatabase taskDatabase;
    private TextField taskInputField;
    private ListView<TaskItem> taskListView;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
    	
    	// Initialize the database
        String currentDirectory = System.getProperty("user.dir");
        taskDatabase = new TaskDatabase(currentDirectory + "\\todolist-database.db");

        // Load tasks from the database and populate the tasks list
        tasks.addAll(taskDatabase.getAllTasks());

    	// Create UI elements
        taskInputField = new TextField();
        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");
        Button clearCompletedButton = new Button("Clear Completed");
        taskListView = new ListView<>(tasks);
        
        // Handle add button click
        addButton.setOnAction(e -> addTask());

        // Handle delete button click
        deleteButton.setOnAction(e -> deleteTask());
        
        // Handle clear completed button click
        clearCompletedButton.setOnAction(e -> clearCompletedTasks());
        
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
                    checkBox.setOnAction(event -> { 
                    boolean completed = checkBox.isSelected();
                    task.setCompleted(completed);
                    
                    // Update the completion status in the database
                    taskDatabase.updateTaskCompletionStatus(task.getId(), completed);
                });
                    setGraphic(checkBox);
                }
            }
        });

    }
    
    private void addTask() {
    	String taskName = taskInputField.getText().trim();
        if (!taskName.isEmpty()) {
            TaskItem newTask = new TaskItem(taskDatabase.getLastAssignedId() + 1, taskName, false); 
            tasks.add(newTask);
            taskDatabase.addTask(newTask); // Add task to the database
            taskInputField.clear(); // Clear the text field
        }
    }
    
    private void deleteTask() {
    	TaskItem selectedTask = taskListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            tasks.remove(selectedTask);
            taskDatabase.deleteTask(selectedTask); // Delete task from the database
        }
    }
    
    private void clearCompletedTasks() {
    	tasks.removeIf(task -> {
            if (task.isCompleted()) {
                // Remove completed task from the database
                taskDatabase.deleteTask(task);
                return true; // Remove from the displayed list
            }
            return false; // Keep in the displayed list
        });
    }
    
}



