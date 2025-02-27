package net.cchat.cchatmod.gui.screens;

import net.cchat.cchatmod.data.tasks.Task;
import net.cchat.cchatmod.data.tasks.TaskManager;
import net.cchat.cchatmod.data.tasks.TaskStatus;
import com.mojang.blaze3d.vertex.PoseStack;
import net.cchat.cchatmod.gui.components.AnimatedButton;
import net.cchat.cchatmod.gui.components.NotificationManager;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

public class TaskScreen extends Screen {
    private final TaskManager taskManager;
    private TaskStatus currentStatus = TaskStatus.ACTIVE;
    private Task selectedTask;

    private static final int COLOR_LEFT_PANEL = 0xAA2D2D2D;
    private static final int COLOR_RIGHT_PANEL = 0xAA1E1E1E;
    private static final int COLOR_PANEL_BORDER = 0xFF888888;
    private static final int COLOR_TEXT = 0xFFFFFFFF;
    private static final int COLOR_ACCENT = 0xFFFFD700;

    private static final int PANEL_PADDING = 10;
    private static final int BUTTON_HEIGHT = 20;
    private static final int BUTTON_SPACING = 10;
    private static final int TASK_PANEL_HEIGHT = 40;
    private static final int TASK_VERTICAL_SPACING = 5;
    private static final int MAX_VISIBLE_TASKS = 8;

    private float accentAnimationProgress = 0.0f;
    private float detailAnimationProgress = 0.0f;

    private AnimatedButton activeTabButton;
    private AnimatedButton completedTabButton;
    private AnimatedButton failedTabButton;

    public TaskScreen(TaskManager taskManager) {
        super(Component.literal("Меню заданий"));
        this.taskManager = taskManager;
    }

    @Override
    protected void init() {
        int leftPanelWidth = this.width / 4;
        int btnY = PANEL_PADDING;
        int spacing = BUTTON_SPACING;
        int availableWidth = leftPanelWidth - 2 * PANEL_PADDING - 2 * spacing;
        int buttonWidth = availableWidth / 3;
        int startX = PANEL_PADDING;

        activeTabButton = new AnimatedButton(startX, btnY, buttonWidth, BUTTON_HEIGHT, Component.literal("★"),
                button -> {
                    currentStatus = TaskStatus.ACTIVE;
                    selectedTask = null;
                    detailAnimationProgress = 0.0f;
                    accentAnimationProgress = 0.0f;
                },
                0xFF3C3C3C, 0xFF5C5C5C);
        completedTabButton = new AnimatedButton(startX + buttonWidth + spacing, btnY, buttonWidth, BUTTON_HEIGHT, Component.literal("✔"),
                button -> {
                    currentStatus = TaskStatus.COMPLETED;
                    selectedTask = null;
                    detailAnimationProgress = 0.0f;
                    accentAnimationProgress = 0.0f;
                },
                0xFF3C3C3C, 0xFF5C5C5C);
        failedTabButton = new AnimatedButton(startX + (buttonWidth + spacing) * 2, btnY, buttonWidth, BUTTON_HEIGHT, Component.literal("✖"),
                button -> {
                    currentStatus = TaskStatus.FAILED;
                    selectedTask = null;
                    detailAnimationProgress = 0.0f;
                    accentAnimationProgress = 0.0f;
                },
                0xFF3C3C3C, 0xFF5C5C5C);

        this.addRenderableWidget(activeTabButton);
        this.addRenderableWidget(completedTabButton);
        this.addRenderableWidget(failedTabButton);

        int listStartY = btnY + BUTTON_HEIGHT + 10;
        List<Task> tasks = taskManager.getTasksByStatus(currentStatus);
        if (!tasks.isEmpty()) {
            selectedTask = tasks.get(0);
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        activeTabButton.setActive(currentStatus == TaskStatus.ACTIVE);
        completedTabButton.setActive(currentStatus == TaskStatus.COMPLETED);
        failedTabButton.setActive(currentStatus == TaskStatus.FAILED);

        renderBackground(poseStack);
        int leftPanelWidth = this.width / 4;
        int rightPanelX = leftPanelWidth;
        Screen.fill(poseStack, 0, 0, leftPanelWidth, this.height, COLOR_LEFT_PANEL);
        Screen.fill(poseStack, rightPanelX, 0, this.width, this.height, COLOR_RIGHT_PANEL);

        int listStartY = PANEL_PADDING + BUTTON_HEIGHT + 10;
        List<Task> tasks = taskManager.getTasksByStatus(currentStatus);
        int visibleCount = Math.min(tasks.size(), MAX_VISIBLE_TASKS);
        for (int i = 0; i < visibleCount; i++) {
            Task task = tasks.get(i);
            int taskPanelY = listStartY + i * (TASK_PANEL_HEIGHT + TASK_VERTICAL_SPACING);
            Screen.fill(poseStack, PANEL_PADDING, taskPanelY, leftPanelWidth - PANEL_PADDING, taskPanelY + TASK_PANEL_HEIGHT, 0xFF3C3C3C);
            drawRectOutline(poseStack, PANEL_PADDING, taskPanelY, leftPanelWidth - PANEL_PADDING, taskPanelY + TASK_PANEL_HEIGHT, COLOR_PANEL_BORDER);
            drawCenteredString(poseStack, font, task.getTitle(), leftPanelWidth / 2, taskPanelY + 8, COLOR_TEXT);
            drawCenteredString(poseStack, font, "Автор: " + task.getAuthor(), leftPanelWidth / 2, taskPanelY + 22, 0xFFAAAAAA);

            if (selectedTask == task) {
                accentAnimationProgress = Math.min(1.0f, accentAnimationProgress + 0.05f);
                int borderOffset = (int) (2 * accentAnimationProgress);
                drawRectOutline(poseStack,
                        PANEL_PADDING - borderOffset,
                        taskPanelY - borderOffset,
                        leftPanelWidth - PANEL_PADDING + borderOffset,
                        taskPanelY + TASK_PANEL_HEIGHT + borderOffset,
                        COLOR_ACCENT);
            }
        }

        if (selectedTask == null) {
            int rightPanelWidth = this.width - rightPanelX;
            drawCenteredString(poseStack, font, "Нажмите на задание для просмотра деталей", rightPanelX + rightPanelWidth / 2, this.height / 2, COLOR_TEXT);
        } else {
            if (detailAnimationProgress < 1.0f) {
                detailAnimationProgress = Math.min(1.0f, detailAnimationProgress + 0.02f);
            }
            int verticalOffset = (int) ((1.0f - detailAnimationProgress) * 20);
            int detailY = PANEL_PADDING + verticalOffset;
            int detailX = rightPanelX + PANEL_PADDING;
            int detailWidth = this.width - rightPanelX - 2 * PANEL_PADDING;
            Screen.fill(poseStack, detailX, detailY, detailX + detailWidth, this.height - PANEL_PADDING, 0xFF2A2A2A);
            drawRectOutline(poseStack, detailX, detailY, detailX + detailWidth, this.height - PANEL_PADDING, COLOR_PANEL_BORDER);
            drawCenteredString(poseStack, font, selectedTask.getTitle(), detailX + detailWidth / 2, detailY + 10, COLOR_TEXT);
            int objectivesY = detailY + 30;
            drawStringWithShadow(poseStack, font, "Цели:", detailX + 10, objectivesY, COLOR_TEXT);
            int j = 0;
            for (String obj : selectedTask.getObjectives()) {
                drawStringWithShadow(poseStack, font, "- " + obj, detailX + 20, objectivesY + 15 + j * 14, 0xFFCCCCCC);
                j++;
            }
            int descY = objectivesY + 15 + j * 14 + 10;
            drawStringWithShadow(poseStack, font, "Описание:", detailX + 10, descY, COLOR_TEXT);
            String[] descLines = selectedTask.getDescription().split("\n");
            int k = 0;
            for (String line : descLines) {
                drawStringWithShadow(poseStack, font, line, detailX + 20, descY + 15 + k * 12, 0xFFCCCCCC);
                k++;
            }
        }

        NotificationManager.renderNotifications(poseStack, font, eventDeltaTime(partialTick));
        super.render(poseStack, mouseX, mouseY, partialTick);
    }

    private float eventDeltaTime(float partialTick) {
        return 0.05f;
    }

    private void drawRectOutline(PoseStack poseStack, int x1, int y1, int x2, int y2, int color) {
        Screen.fill(poseStack, x1, y1, x2, y1 + 1, color);
        Screen.fill(poseStack, x1, y2 - 1, x2, y2, color);
        Screen.fill(poseStack, x1, y1, x1 + 1, y2, color);
        Screen.fill(poseStack, x2 - 1, y1, x2, y2, color);
    }

    private void drawHorizontalLine(PoseStack poseStack, int x1, int x2, int y, int color) {
        Screen.fill(poseStack, x1, y, x2, y + 1, color);
    }

    private void drawVerticalLine(PoseStack poseStack, int x, int y1, int y2, int color) {
        Screen.fill(poseStack, x, y1, x + 1, y2, color);
    }

    private void drawStringWithShadow(PoseStack poseStack, net.minecraft.client.gui.Font font, String text, int x, int y, int color) {
        font.drawShadow(poseStack, text, x, y, color);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int leftPanelWidth = this.width / 4;
        if (mouseX < leftPanelWidth) {
            int listStartY = PANEL_PADDING + BUTTON_HEIGHT + 10;
            List<Task> tasks = taskManager.getTasksByStatus(currentStatus);
            int visibleCount = Math.min(tasks.size(), MAX_VISIBLE_TASKS);
            for (int i = 0; i < visibleCount; i++) {
                int taskPanelY = listStartY + i * (TASK_PANEL_HEIGHT + TASK_VERTICAL_SPACING);
                if (mouseY >= taskPanelY && mouseY <= taskPanelY + TASK_PANEL_HEIGHT) {
                    selectedTask = tasks.get(i);
                    detailAnimationProgress = 0.0f;
                    accentAnimationProgress = 0.0f;
                    break;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void onClose() {
        taskManager.saveTasks();
        super.onClose();
    }
}
