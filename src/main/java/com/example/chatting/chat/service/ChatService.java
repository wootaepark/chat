package com.example.chatting.chat.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.example.chatting.chat.dto.ChatRoomDto;
import com.example.chatting.chat.dto.CreateRoomReqDto;
import com.example.chatting.chat.entity.ChatRoom;
import com.example.chatting.chat.entity.Member;
import com.example.chatting.chat.entity.MemberChatRoom;
import com.example.chatting.chat.repository.ChatRoomRepository;
import com.example.chatting.chat.repository.MemberChatRoomRepository;
import com.example.chatting.chat.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

	private final ObjectMapper objectMapper;
	private final ChatRoomRepository chatRoomRepository;
	private final MemberRepository memberRepository;
	private final MemberChatRoomRepository memberChatRoomRepository;

	private final Map<String, ChatRoomDto> chatRoomMap = new ConcurrentHashMap<>();
	// <채팅방 UUID, 채팅 방정보 DTO>

	private final static Long USER_ID = 1L; // 토론방 개최자

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
				.roomName(chatRoom.getRoomName())
				.build();
			chatRoomMap.put(id, room);
		}
		return room;

	}

	@Transactional
	public ChatRoom createRoom(CreateRoomReqDto reqDto) {
		Member member = memberRepository.findById(USER_ID)
			.orElseThrow(() -> new RuntimeException("Member not found"));

		String randomId = UUID.randomUUID().toString();

		ChatRoom chatRoom = ChatRoom.builder()
			.roomId(randomId)
			.roomName(reqDto.getRoomName())
			.member(member)
			.build();

		MemberChatRoom memberChatRoom = MemberChatRoom.builder()
			.chatRoom(chatRoom)
			.member(member)
			.build();

		memberChatRoomRepository.save(memberChatRoom);
		chatRoomRepository.save(chatRoom);

		return chatRoom;
	}

	@Transactional
	public void addMemberToRoom(String roomId, String sender) {

		ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId)
			.orElseThrow(() -> new RuntimeException("Room not found"));

		Member member = memberRepository.findByName(sender)
			.orElseThrow(() -> new RuntimeException("Member not found"));

		boolean isExistMemberChatRoom = memberChatRoomRepository.existsByMemberIdAndChatRoomId(member.getId(),
			chatRoom.getId());

		if (!isExistMemberChatRoom) {
			MemberChatRoom memberChatRoom = MemberChatRoom.builder()
				.chatRoom(chatRoom)
				.member(member)
				.build();
			memberChatRoomRepository.save(memberChatRoom);
		}

		// db 호출 3번 이거 최적화 필요 있음 
		// 비록 유저가 추가될 때마다 실행되는 트랜잭션이지만 그래도 필요 있음
		// 만약 채팅을 칠 때마다 이런 트랜잭션이 수행되면 죽음임
	}

	public <T> void sendMessage(WebSocketSession session, T message) {
		try {
			session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	public void removeSessionFromRoom(String sessionId) {
		for (ChatRoomDto chatRoom : chatRoomMap.values()) {
			chatRoom.getSessions().removeIf(session -> session.getId().equals(sessionId));
		}
	}
}
