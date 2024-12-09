package com.example.chatting.chat.entity;

import java.util.HashMap;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoom {
	private String roomId;
	private String roomName;
	private long userCount;
	private HashMap<String, String> userList = new HashMap<>();
	// private Set<WebSocketSession> sessions = new HashSet<>(); // STOMP 에서는 세션이 필요가 없다.

	public static ChatRoom create(String roomName) {
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.roomId = UUID.randomUUID().toString();
		chatRoom.roomName = roomName;

		return chatRoom;
	}

}
