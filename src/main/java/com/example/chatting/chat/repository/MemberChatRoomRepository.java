package com.example.chatting.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.chatting.chat.entity.MemberChatRoom;

public interface MemberChatRoomRepository extends JpaRepository<MemberChatRoom, Long> {
	boolean existsByMemberIdAndChatRoomId(Long memberId, Long chatRoomId);
}
