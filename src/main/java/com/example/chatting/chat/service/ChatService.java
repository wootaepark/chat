package com.example.chatting.chat.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.example.chatting.chat.dto.ChatRoomDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
	private final ObjectMapper objectMapper;
	private Map<String, ChatRoomDto> chatRooms; // 일단 in-memory 저장

	@PostConstruct
	private void init() {
		chatRooms = new HashMap<>();
	}

	public List<ChatRoomDto> findAllRoom() {
		return new ArrayList<>(chatRooms.values());
	}

	public ChatRoomDto findRoomById(String id) {
		return chatRooms.get(id);
	}

	public ChatRoomDto createRoom(String roomName) {
		String randomId = UUID.randomUUID().toString();
		ChatRoomDto chatRoomDto = ChatRoomDto.builder()
			.roomId(randomId)
			.roomName(roomName)
			.build();
		chatRooms.put(randomId, chatRoomDto);
		return chatRoomDto;
	}

	public <T> void sendMessage(WebSocketSession session, T message) {
		try {
			session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}
