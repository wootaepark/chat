package com.example.chatting.chat.entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.WebSocketSession;

import com.example.chatting.chat.dto.ChatMessageDto;
import com.example.chatting.chat.service.ChatService;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoom {
	private String roomId;
	private String roomName;
	private Set<WebSocketSession> sessions = new HashSet<>();

	@Builder
	public ChatRoom(String roomId, String roomName) {
		this.roomId = roomId;
		this.roomName = roomName;
	}

	public void handlerActions(WebSocketSession session, ChatMessageDto chatMessageDto, ChatService chatService) {
		if (chatMessageDto.getMessageType().equals(ChatMessageDto.MessageType.ENTER)) {
			sessions.add(session);
			chatMessageDto.setMessage(chatMessageDto.getSender() + "님이 입장했습니다.");
		}
		sendMessage(chatMessageDto, chatService);
	}

	private <T> void sendMessage(T message, ChatService chatService) {
		sessions.parallelStream()
			.forEach(session -> chatService.sendMessage(session, message));
	}
}
