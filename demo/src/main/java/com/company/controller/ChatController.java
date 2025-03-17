package com.company.controller;

import com.company.dto.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    // 클라이언트가 "/app/chat.sendMessage"로 메시지를 보내면,
    // "/topic/public" 구독자들에게 메시지를 전달
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        return chatMessage;
    }

    // 클라이언트가 "/app/chat.addUser"로 메시지를 보내면,
    // "/topic/public" 구독자들에게 메시지를 전달
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(ChatMessage chatMessage) {
        return chatMessage;
    }
}
