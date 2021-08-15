package com.kmat100.bulletin_board1.domain.repository;

import com.kmat100.bulletin_board1.domain.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    List<BoardEntity> findByTitleContaining(String keyword);
//    검색을 직접적으로 호출하는 메서드,
}
