package com.example.chatting.chat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String roomId;

	private String roomName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hostId")
	private Member member;

	@Builder
	public ChatRoom(String roomId, String roomName, Member member) {
		this.roomId = roomId;
		this.roomName = roomName;
		this.member = member;
	}

}
