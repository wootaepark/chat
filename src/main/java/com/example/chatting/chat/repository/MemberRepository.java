package com.example.chatting.chat.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.chatting.chat.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByName(String sender);
}
