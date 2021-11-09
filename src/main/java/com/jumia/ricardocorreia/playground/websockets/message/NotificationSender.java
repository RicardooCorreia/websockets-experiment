package com.jumia.ricardocorreia.playground.websockets.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationSender {

    public static final String DESTINATION = "/topic/notifications";

    private final SimpMessageSendingOperations messagingTemplate;

    private final ObjectMapper objectMapper;

    public void send(String notificationReceiver, String notificationText) throws JsonProcessingException {

        final Message message = Message.of(notificationText);
        final String content = objectMapper.writeValueAsString(message);
        final MessageHeaders headers = createHeaders(notificationReceiver);

        messagingTemplate.convertAndSendToUser(notificationReceiver, DESTINATION, content, headers);
    }

    private MessageHeaders createHeaders(String sessionId) {

        final SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        return headerAccessor.getMessageHeaders();
    }

    @Value(staticConstructor = "of")
    private static class Message {

        String content;
    }
}
