package com.jumia.ricardocorreia.playground.websockets.domain.humanresources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jumia.ricardocorreia.playground.websockets.message.NotificationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class NotificationModel {

    private final NotificationSender messageSender;

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    public void processRequest(String notificationTo, String notificationText) {

        executorService.submit(() -> {
            try {
                messageSender.send(notificationTo, notificationText);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }
}
