public class SubTask extends Task {
    private Epic epic;

    public SubTask(String title, String description, Status status){
        super(title, description, status);
    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic){
        this.epic = epic;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "epicId=" + getEpic().getId() +
                ", id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                '}';
    }
}
