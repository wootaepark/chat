package com.example.chatting.chat.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessageDto {
	public enum MessageType {
		ENTER, TALK
	}

	private MessageType messageType;
	private String roomId; // 방번호
	private String sender; // 메시지 보낸 사람
	private String message; // 메시지 내용

	@Builder
	public ChatMessageDto(MessageType messageType, String roomId, String sender, String message) {
		this.messageType = messageType;
		this.roomId = roomId;
		this.sender = sender;
		this.message = message;
	}
}
