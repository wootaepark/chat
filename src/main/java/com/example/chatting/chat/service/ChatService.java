package com.example.chatting.chat.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
	/*private final ObjectMapper objectMapper;
	private Map<String, ChatRoom> chatRooms; // 일단 in-memory 저장

	@PostConstruct
	private void init() {
		chatRooms = new HashMap<>();
	}

	public List<ChatRoom> findAllRoom() {
		return new ArrayList<>(chatRooms.values());
	}

	public ChatRoom findRoomById(String id) {
		return chatRooms.get(id);
	}

	public ChatRoom createRoom(String roomName) {
		String randomId = UUID.randomUUID().toString();
		ChatRoom chatRoom = ChatRoom.builder()
			.roomId(randomId)
			.roomName(roomName)
			.build();
		chatRooms.put(randomId, chatRoom);
		return chatRoom;
	}

	public <T> void sendMessage(WebSocketSession session, T message) {
		try {
			session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}*/
}
