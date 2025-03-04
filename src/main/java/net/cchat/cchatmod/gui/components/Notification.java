package net.cchat.cchatmod.gui.components;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;

public class Notification {
    private final String message;
    private final float duration;
    private final long creationTime;
    private static final float DESCEND_FRACTION = 0.83f;
    private static final int START_Y = 10;
    private static final int END_Y = 150;

    public Notification(String message, float duration) {
        this.message = message;
        this.duration = duration;
        this.creationTime = System.currentTimeMillis();
    }

    public String getMessage() {
        return message;
    }

    public float getProgress() {
        long now = System.currentTimeMillis();
        return Math.min((now - creationTime) / (duration * 1300f), 1.0f);
    }

    public boolean isExpired() {
        return getProgress() >= 0.9f;
    }

    public void render(PoseStack poseStack, Font font) {
        float progress = getProgress();
        int currentY;

        if (progress < DESCEND_FRACTION) {
            float t = progress / DESCEND_FRACTION;
            float eased = 1 - (1 - t) * (1 - t);
            currentY = START_Y + (int) ((END_Y - START_Y) * eased);
        } else {
            currentY = END_Y;
        }

        if (progress >= 0.85f) {
            return;
        }

        //int currentY = START_Y + (int)((END_Y - START_Y) * progress);

        int bgColor = (6 << 24);

        int padding = 4;
        int textWidth = font.width(message);
        int boxWidth = textWidth + padding * 2;
        int boxHeight = font.lineHeight + padding * 2;
        int boxX = 10;
        int boxY = currentY;

        Screen.fill(poseStack, boxX, boxY, boxX + boxWidth, boxY + boxHeight, bgColor);
        font.drawShadow(poseStack, message, boxX + padding, boxY + padding, 0xFFFFFF);
    }
}