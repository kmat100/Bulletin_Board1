package com.kmat100.bulletin_board1.domain.repository;

import com.kmat100.bulletin_board1.domain.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
}
