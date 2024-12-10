package com.example.chatting.chat.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.example.chatting.chat.dto.ChatRoomDto;
import com.example.chatting.chat.dto.CreateRoomReqDto;
import com.example.chatting.chat.entity.ChatRoom;
import com.example.chatting.chat.repository.ChatRoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

	private final ObjectMapper objectMapper;
	private final ChatRoomRepository chatRoomRepository;

	private final Map<String, ChatRoomDto> chatRoomMap = new ConcurrentHashMap<>();

	public List<ChatRoom> findAllRoom() {
		List<ChatRoom> rooms = chatRoomRepository.findAll();
		return new ArrayList<>(rooms);
	}

	public ChatRoomDto findRoomById(String id) {
		ChatRoomDto room = chatRoomMap.get(id);
		if (room == null) {
			ChatRoom chatRoom = chatRoomRepository.findByRoomId(id)
				.orElseThrow(() -> new RuntimeException("Room not found"));
			room = ChatRoomDto.builder()
				.roomId(chatRoom.getRoomId())
				.roomName(chatRoom.getTitle())
				.build();
			chatRoomMap.put(id, room);
		}
		return room;

	}

	public ChatRoom createRoom(CreateRoomReqDto reqDto) {
		String randomId = UUID.randomUUID().toString();
		ChatRoom chatRoom = ChatRoom.builder()
			.roomId(randomId)
			.title(reqDto.getTitle())
			.build();

		chatRoomRepository.save(chatRoom);

		return chatRoom;
	}

	public <T> void sendMessage(WebSocketSession session, T message) {
		try {
			session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}
