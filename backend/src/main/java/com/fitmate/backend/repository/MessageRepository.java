package com.fitmate.backend.repository;

import com.fitmate.backend.entity.ChatRoom;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> findAllByChatRoomId(Long id);
    List<Message> findAllByChatRoomId(Long id, Pageable pageable);
    void deleteAllByChatRoom(ChatRoom chatRoom);
}
