package com.example.chatting.chat.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatting.chat.entity.ChatRoom;
import com.example.chatting.chat.service.ChatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
	private final ChatService chatService;

	@PostMapping
	public ChatRoom createRoom(@RequestBody String name) {
		return chatService.createRoom(name);
	}

	@GetMapping
	public List<ChatRoom> findAllRooms() {
		return chatService.findAllRoom();
		// 유저가 Session 에 들어가고 나서 요청 시에 오류가 발생
		/*
		 {
        "roomId": "e9f6ad85-9aa2-4172-9d86-306311e9cd00",
        "roomName": "{\r\n    \"name\" : \"채팅방2\"\r\n}",
        "sessions": [] // <- 여기에 뭐가 들어가면 json parsing 에러 발생
        }
		 */
	}
}
