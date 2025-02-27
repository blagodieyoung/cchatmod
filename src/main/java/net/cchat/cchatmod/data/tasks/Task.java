package net.cchat.cchatmod.data.tasks;

import java.util.ArrayList;
import java.util.List;

public class Task {
    private String title;
    private List<String> objectives;
    private String description;
    private String author;
    private TaskStatus status;

    // Конструктор по умолчанию (требуется для Gson)
    public Task() {
        this.objectives = new ArrayList<>();
        this.status = TaskStatus.ACTIVE;
    }

    public Task(String title, String description, String author) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.status = TaskStatus.ACTIVE;
        this.objectives = new ArrayList<>();
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<String> getObjectives() { return objectives; }
    public void setObjectives(List<String> objectives) { this.objectives = objectives; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }

    public void addObjective(String objective) {
        objectives.add(objective);
    }

    public void appendDescription(String additionalText) {
        description += "\n" + additionalText;
    }
}