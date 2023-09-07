package application;

public class TaskItem {
	
    private int id; // Unique task ID
    private String name;
    private boolean completed;

    public TaskItem(int id, String name, boolean completed) {
    	this.id = id;
        this.name = name;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public void setCompleted(boolean completed) {
    	this.completed = completed;
    }
    
    public boolean isCompleted() {
        return completed;
    }
    
    @Override
    public String toString() {
        return name; // Return the task name as the string representation
    }
    
}



