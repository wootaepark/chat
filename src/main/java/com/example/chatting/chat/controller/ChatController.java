package com.example.chatting.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatting.chat.dto.ChatMessageDto;
import com.example.chatting.chat.repository.ChatRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {

	// 기능 : 입장 및 채팅 송신

	private final SimpMessageSendingOperations template;
	private final ChatRepository chatRepository;

	@MessageMapping("/enterUser") // 유저 방 입장
	public void enterUser(@Payload ChatMessageDto chatMessageDto, SimpMessageHeaderAccessor headerAccessor) {
		chatRepository.plusUserCnt(chatMessageDto.getRoomId());
		String userUUID = chatRepository.addUser(chatMessageDto.getRoomId(), chatMessageDto.getSender());

		headerAccessor.getSessionAttributes().put("userUUID", userUUID);
		headerAccessor.getSessionAttributes().put("roomId", chatMessageDto.getRoomId());

		chatMessageDto.setMessage(chatMessageDto.getSender() + "님 입장!");
		template.convertAndSend("/sub/chat/room/" + chatMessageDto.getRoomId(), chatMessageDto);
	}

	@MessageMapping("/sendMessage")
	public void sendMessage(@Payload ChatMessageDto chatMessageDto) {
		log.info("CHAT {}", chatMessageDto);
		chatMessageDto.setMessage(chatMessageDto.getMessage());
		template.convertAndSend("/sub/chat/room/" + chatMessageDto.getRoomId(), chatMessageDto);
	}

}
