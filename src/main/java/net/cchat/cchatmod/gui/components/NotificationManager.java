package net.cchat.cchatmod.gui.components;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class NotificationManager {
    private static final Queue<Notification> notificationQueue = new LinkedList<>();

    public static void addNotification(String message) {
        notificationQueue.offer(new Notification(message, 3.0f));
    }

    public static void renderNotifications(PoseStack poseStack, Font font, float deltaTime) {
        int index = 0;
        Iterator<Notification> it = notificationQueue.iterator();
        while (it.hasNext()) {
            Notification n = it.next();
            n.render(poseStack, font, index);
            index++;
        }

        while (!notificationQueue.isEmpty() && notificationQueue.peek().isExpired()) {
            notificationQueue.poll();
        }
    }
}