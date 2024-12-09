package com.example.chatting.chat.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.example.chatting.chat.dto.ChatMessageDto;
import com.example.chatting.chat.entity.ChatRoom;
import com.example.chatting.chat.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketChatHandler extends TextWebSocketHandler {

	private final ObjectMapper objectMapper;
	private final ChatService chatService;

	Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

	@Override // 웹 소켓 연결 시
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.put(session.getId(), session); // 세션 객체를 생성하는 부분
		session.sendMessage(new TextMessage("첫 채팅입니다."));

	}

	/*@Override // 세션에 메시지 전송
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		// 브로드캐스팅 방식으로 세션 접속한 모든 이에게 전송
		String payload = "세션 아이디 " + session.getId() + "내용 : " + message.getPayload() + "  현재시간" + LocalDateTime.now();
		TextMessage textMessage = new TextMessage(payload);

		for (WebSocketSession webSocketSession : sessions.values()) {
			if (webSocketSession.isOpen()) {
				webSocketSession.sendMessage(textMessage);
			}
		}
	}*/

	@Override // 세션에 메시지 전송
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		log.info("payload {}", payload);

		ChatMessageDto chatMessageDto = objectMapper.readValue(payload, ChatMessageDto.class);
		ChatRoom room = chatService.findRoomById(chatMessageDto.getRoomId());
		room.handlerActions(session, chatMessageDto, chatService);
	}

	// 추가 필요 로직
	// 한 채팅방에는 두 명 이상의 유저가 들어올 수 없다.
	// 비즈니스 로직 상에서 교환 등록된 상품을 얻고 싶은 유저가 채팅을 신청하는 방식 (초대 느낌)
	// 두 멤버는 방을 나갈 수 있으며, 만약 다시 채팅을 하고 싶으면 채팅 다시 하기를 통해서 다시 채팅을 요청 할 수 있고 이것을 한쪽이 수락해야 채팅이 다시 이루어진다.
	// 채팅 로그 저장 및 채팅 시간 표시 
	// + 이미지 업로드 되면 좋을 듯
	// 읽음, 안읽음 기능 추가하면 좋을 듯
}
