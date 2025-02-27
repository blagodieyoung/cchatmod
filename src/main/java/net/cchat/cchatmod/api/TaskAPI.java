package net.cchat.cchatmod.api;

import net.cchat.cchatmod.data.tasks.Task;
import net.cchat.cchatmod.data.tasks.TaskManager;
import net.cchat.cchatmod.data.tasks.TaskStatus;
import net.cchat.cchatmod.gui.components.NotificationManager;

public class TaskAPI {
    private final TaskManager taskManager;

    public TaskAPI(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public void addTask(String title, String goal, String description, String author) {
        Task task = new Task(title, description, author);
        task.addObjective(goal);
        taskManager.addTask(task);
        NotificationManager.addNotification("Новое задание: " + title);
    }

    public void updateTaskDescription(Task task, String newDescription) {
        task.setDescription(newDescription);
        NotificationManager.addNotification("Задание обновлено: " + task.getTitle());
    }

    public void appendTaskDescription(Task task, String additionalText) {
        task.appendDescription(additionalText);
        NotificationManager.addNotification("Задание обновлено: " + task.getTitle());
    }

    public void deleteTask(Task task) {
        taskManager.getTasks().remove(task);
        NotificationManager.addNotification("Задание удалено: " + task.getTitle());
    }

    public void clearAllTasks() {
        taskManager.clearTasks();
        NotificationManager.addNotification("Все задания удалены.");
    }

    public void moveTaskToStatus(Task task, TaskStatus newStatus) {
        task.setStatus(newStatus);
        NotificationManager.addNotification("Статус изменен: " + task.getTitle());
    }

    public Task getTaskByTitle(String title) {
        return taskManager.getTasks().stream()
                .filter(t -> t.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }
}
