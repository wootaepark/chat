package com.example.chatting.chat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatting.chat.dto.CreateChatRoomReqDto;
import com.example.chatting.chat.dto.FindChatRoomReqDto;
import com.example.chatting.chat.entity.ChatRoom;
import com.example.chatting.chat.repository.ChatRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatroom")
public class ChatRoomController {

	// 기능 : 채팅방 리스트, 채팅방 생성, 채팅 참여 유저 리스트 반환
	private final ChatRepository chatRepository;

	@GetMapping("/")
	public List<ChatRoom> goChatRoom() {
		List<ChatRoom> chatRooms = chatRepository.findAllRoom();
		System.out.println(chatRooms.get(0).getRoomId()); // 임시로 첫번째만
		return chatRooms;
	}

	@PostMapping("/room")
	public ResponseEntity<?> addChatRoom(@RequestBody CreateChatRoomReqDto reqDto) {
		ChatRoom room = chatRepository.createChatRoom(reqDto);
		return ResponseEntity.ok(room);
	}

	@GetMapping("/userlist")
	public List<String> userList(@RequestBody FindChatRoomReqDto reqDto) {
		return chatRepository.getUserList(reqDto);
	}

}
