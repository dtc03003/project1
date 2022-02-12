package com.fitmate.backend.repository;

import com.fitmate.backend.entity.Style;
import com.fitmate.backend.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByStyleAndTag(Style style, String tag);
    List<Tag> findAllByStyle(Style style);
    List<Tag> findAllByTag(String tag);
    void deleteAllByStyle(Style style);
}
