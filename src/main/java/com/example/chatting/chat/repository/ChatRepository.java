package com.example.chatting.chat.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.chatting.chat.dto.CreateChatRoomReqDto;
import com.example.chatting.chat.dto.FindChatRoomReqDto;
import com.example.chatting.chat.entity.ChatRoom;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class ChatRepository {

	private Map<String, ChatRoom> chatRoomMap = new LinkedHashMap<>(); // 추후 db에 (mongodb 에 저장될 예정)

	// 전체 채팅방 조회
	public List<ChatRoom> findAllRoom() {
		List<ChatRoom> charRooms = new ArrayList<>(chatRoomMap.values());
		Collections.reverse(charRooms); // 최신순

		return charRooms;
	}

	// 특정 채팅방 조회
	public ChatRoom findRoomById(String id) {
		return chatRoomMap.get(id);
	}

	// 채팅방 생성
	public ChatRoom createChatRoom(CreateChatRoomReqDto reqDto) {
		ChatRoom chatRoom = ChatRoom.create(reqDto.getRoomName());
		chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
		return chatRoom;
	}

	// 채팅방 인원 +1
	public void plusUserCnt(String roomId) {
		ChatRoom chatRoom = chatRoomMap.get(roomId);
		chatRoom.setUserCount(chatRoom.getUserCount() + 1);
	}

	// 채팅방 인원 -1
	public void minusUserCnt(String roomId) {
		ChatRoom chatRoom = chatRoomMap.get(roomId);
		chatRoom.setUserCount(chatRoom.getUserCount() - 1);
	}

	// 채팅방 유저 리스트에 유저 추가
	public String addUser(String roomId, String userName) {
		ChatRoom chatRoom = chatRoomMap.get(roomId);
		String userUUID = UUID.randomUUID().toString();

		chatRoom.getUserList().put(userUUID, userName);

		return userUUID;
	}

	// 채팅방 유저 리스트 삭제
	public void delUser(String roomId, String userName) {
		ChatRoom chatRoom = chatRoomMap.get(roomId);
		chatRoom.getUserList().remove(userName);
	}

	// 채팅방 userName 조회
	public String getUserName(String roomId, String userUUID) {
		ChatRoom chatRoom = chatRoomMap.get(roomId);
		return chatRoom.getUserList().get(userUUID);
	}

	// 채팅방 전체 userList 조회
	public List<String> getUserList(FindChatRoomReqDto reqDto) {
		List<String> list = new ArrayList<>();
		System.out.println("roomId  :  " + reqDto.getRoomId());
		ChatRoom chatRoom = chatRoomMap.get(reqDto.getRoomId());
		System.out.println("chatRoom  : " + chatRoom);

		chatRoom.getUserList().forEach((key, value) -> list.add(value));
		return list;
	}

}
