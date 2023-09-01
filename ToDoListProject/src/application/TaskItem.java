package application;

public class TaskItem {
    private String name;
    private boolean completed;

    public TaskItem(String name) {
        this.name = name;
        this.completed = false; // Initialize as not completed
    }

    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
