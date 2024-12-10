package com.example.chatting.chat.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatting.chat.dto.CreateRoomReqDto;
import com.example.chatting.chat.entity.ChatRoom;
import com.example.chatting.chat.service.ChatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
	private final ChatService chatService;

	@PostMapping
	public ChatRoom createRoom(@RequestBody CreateRoomReqDto reqDto) {
		return chatService.createRoom(reqDto);
	}

	@GetMapping
	public List<ChatRoom> findAllRooms() {
		return chatService.findAllRoom();
	}
}
