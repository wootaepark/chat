package com.example.chatting.chat.dto;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.WebSocketSession;

import com.example.chatting.chat.service.ChatService;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomDto {
	private String roomId;
	private String roomName;
	private Set<WebSocketSession> sessions = new HashSet<>();
	// 해당 채팅방 유저의 세션

	@Builder
	public ChatRoomDto(String roomId, String roomName) {
		this.roomId = roomId;
		this.roomName = roomName;
	}

	public void handlerActions(WebSocketSession session, ChatMessageDto chatMessageDto, ChatService chatService) {
		if (chatMessageDto.getMessageType().equals(ChatMessageDto.MessageType.ENTER)) {
			sessions.add(session);
			chatMessageDto.setMessage(chatMessageDto.getSender() + " 님이 입장했습니다.");
			// 입장 시 마다 해당 유저를 db 에 추가하고 싶음
			chatService.addMemberToRoom(chatMessageDto.getRoomId(), chatMessageDto.getSender());
		}
		sendMessage(chatMessageDto, chatService);
	}

	private <T> void sendMessage(T message, ChatService chatService) {
		sessions.parallelStream()
			.forEach(session -> chatService.sendMessage(session, message));
	}

}
