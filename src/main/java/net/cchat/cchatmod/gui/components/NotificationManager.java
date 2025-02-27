package net.cchat.cchatmod.gui.components;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;

import java.util.LinkedList;
import java.util.Queue;

public class NotificationManager {
    private static final Queue<Notification> notificationQueue = new LinkedList<>();

    public static void addNotification(String message) {
        if (!notificationQueue.isEmpty()) {
            Notification current = notificationQueue.peek();
            if (current.getMessage().equals(message) && !current.isExpired()) {
                return;
            }
        }
        notificationQueue.offer(new Notification(message, 3.0f));
    }

    public static void renderNotifications(PoseStack poseStack, Font font, float deltaTime) {
        if (notificationQueue.isEmpty()) {
            return;
        }
        Notification current = notificationQueue.peek();
        current.render(poseStack, font);
        if (current.isExpired()) {
            notificationQueue.poll();
        }
    }
}
