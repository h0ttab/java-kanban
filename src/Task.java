public class Task {
    private final String title;
    private final String description;
    private final int id;
    private Status status;

    public Task(String title, String description, Status status) {
        id = TaskManager.taskId++;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public int getId(){
        return id;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public Status getStatus(){
        return status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                '}';
    }
}
