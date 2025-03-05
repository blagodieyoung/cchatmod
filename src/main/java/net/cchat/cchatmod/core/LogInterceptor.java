package net.cchat.cchatmod.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.AbstractAppender;

public class LogInterceptor {
    public static void init() {
        Logger logger = (Logger) LogManager.getLogger("minecraft");
        Appender appender = new AbstractAppender("CustomLogInterceptor", null, null, false, null) {
            @Override
            public void append(LogEvent event) {
                String message = event.getMessage().getFormattedMessage();
            }
        };
        appender.start();
        logger.addAppender(appender);
    }
}
