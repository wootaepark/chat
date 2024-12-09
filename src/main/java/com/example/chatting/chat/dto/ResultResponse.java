package com.example.chatting.chat.dto;

import java.util.List;

import com.example.chatting.chat.entity.ChatRoom;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResultResponse {

	private List<ChatRoom> chatRooms;

}
