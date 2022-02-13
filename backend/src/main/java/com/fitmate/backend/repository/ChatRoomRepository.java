package com.fitmate.backend.repository;

import com.fitmate.backend.entity.ChatRoom;
import com.fitmate.backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findAllByCustomer(Member member);
    List<ChatRoom> findAllByHost(Member member);
}
