package com.fitmate.backend.repository;

import com.fitmate.backend.entity.ChatRoom;
import com.fitmate.backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByHostEqualsAndAccessCode(Member member, String access);
    Optional<ChatRoom> findByHost(Member member);
    Boolean existsByHost(Member member);
}
