package com.chhabinath.chat.whispr.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.chhabinath.chat.whispr.model.ChatMessage;

@Controller
public class ChatController {
	
	// /app/sendMessage
	@MessageMapping("/sendMessage") //client sends messages 
	@SendTo("/topic/messages") //Broadcast the message
	public ChatMessage sendMessage(ChatMessage message) {
		return message;
	}
	
	@GetMapping
	public String chat() {
		return "chat";
	}
}
