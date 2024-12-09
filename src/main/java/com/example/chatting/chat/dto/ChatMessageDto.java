package com.example.chatting.chat.dto;

import java.time.LocalDateTime;

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
		ENTER, TALK, LEAVE
	}

	private MessageType messageType;
	private String roomId; // 방번호
	private String sender; // 메시지 보낸 사람
	private String message; // 메시지 내용
	private String time = LocalDateTime.now().toString(); // 채팅 발송 시간

	@Builder
	public ChatMessageDto(MessageType messageType, String roomId, String sender, String message, String time) {
		this.messageType = messageType;
		this.roomId = roomId;
		this.sender = sender;
		this.message = message;
		this.time = LocalDateTime.now().toString();

	}
}
