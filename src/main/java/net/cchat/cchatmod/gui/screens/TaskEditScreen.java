package net.cchat.cchatmod.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cchat.cchatmod.data.tasks.Task;
import net.cchat.cchatmod.data.tasks.TaskManager;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class TaskEditScreen extends Screen {
    private final TaskManager taskManager;
    private final Task task;
    private EditBox objectiveInput;
    private EditBox descriptionInput;

    private static final int FIELD_WIDTH = 200;
    private static final int FIELD_HEIGHT = 20;
    private static final int V_SPACING = 5;

    public TaskEditScreen(TaskManager taskManager, Task task) {
        super(Component.literal("Редактирование задания"));
        this.taskManager = taskManager;
        this.task = task;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int y = this.height / 4;

        objectiveInput = new EditBox(font, centerX - FIELD_WIDTH / 2, y, FIELD_WIDTH, FIELD_HEIGHT, Component.literal("Новая цель"));
        objectiveInput.setValue("");
        addRenderableWidget(objectiveInput);

        addRenderableWidget(new Button(centerX - FIELD_WIDTH / 2, y + FIELD_HEIGHT + V_SPACING, FIELD_WIDTH, FIELD_HEIGHT, Component.literal("Добавить цель"), button -> {
            String text = objectiveInput.getValue();
            if (!text.trim().isEmpty()) {
                task.addObjective(text.trim());
                objectiveInput.setValue("");
            }
        }));

        y += (FIELD_HEIGHT * 2 + 20);
        descriptionInput = new EditBox(font, centerX - FIELD_WIDTH / 2, y, FIELD_WIDTH, FIELD_HEIGHT, Component.literal("Доп. описание"));
        descriptionInput.setValue("");
        addRenderableWidget(descriptionInput);

        addRenderableWidget(new Button(centerX - FIELD_WIDTH / 2, y + FIELD_HEIGHT + V_SPACING, FIELD_WIDTH, FIELD_HEIGHT, Component.literal("Дополнить описание"), button -> {
            String text = descriptionInput.getValue();
            if (!text.trim().isEmpty()) {
                task.appendDescription(text.trim());
                descriptionInput.setValue("");
            }
        }));

        addRenderableWidget(new Button(centerX - FIELD_WIDTH / 2, y + (FIELD_HEIGHT + V_SPACING) * 2, FIELD_WIDTH, FIELD_HEIGHT, Component.literal("Назад"), button -> {
            minecraft.setScreen(new TaskScreen(taskManager));
        }));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBackground(poseStack);
        drawCenteredString(poseStack, font, "Редактирование задания: " + task.getTitle(), this.width / 2, 20, 0xFFFFFFFF);
        super.render(poseStack, mouseX, mouseY, partialTick);
    }
}