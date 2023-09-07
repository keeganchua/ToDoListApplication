package application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDatabase {

    private Connection connection;

    public TaskDatabase(String databasePath) {
        try {
            // Create or open the SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
            
            // Initialize the database schema (create necessary tables)
            createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTables() {
        try {
            Statement statement = connection.createStatement();

            // Create a table for tasks
            statement.execute("CREATE TABLE IF NOT EXISTS tasks (" +
                    "id INTEGER PRIMARY KEY," +
                    "name TEXT NOT NULL," +
                    "completed BOOLEAN NOT NULL" +
                    ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTask(TaskItem task) {

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO tasks (id, name, completed) VALUES (?, ?, ?)"
            );
            statement.setInt(1, task.getId());
            statement.setString(2, task.getName());
            statement.setBoolean(3, task.isCompleted());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(TaskItem task) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM tasks WHERE id = ?"
            );
            statement.setInt(1, task.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<TaskItem> getAllTasks() {
        List<TaskItem> tasks = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tasks");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                boolean completed = resultSet.getBoolean("completed");
                TaskItem task = new TaskItem(id, name, completed);
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }
    
    public int getLastAssignedId() {
    	String query = "SELECT id FROM tasks";
        List<Integer> idList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                idList.add(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Find the highest ID number in the list
        if(!idList.isEmpty()) {
        	int maxId = 0;
            for(int id : idList) {
            	if(id > maxId) {
            		maxId = id;
            	}
            }
            return maxId;
        } else {
            // Return 0 if no tasks exist in the database yet
            return 0;
        }
    }
    
    public void updateTaskCompletionStatus(int taskId, boolean completed) {
        String updateSQL = "UPDATE tasks SET completed = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateSQL)) {
            statement.setBoolean(1, completed);
            statement.setInt(2, taskId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}

