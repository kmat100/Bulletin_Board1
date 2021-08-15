package com.kmat100.bulletin_board1.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
// access 는 생성자의 접근 권한을 설정하는 속성
@Getter
@Entity
// 객체를 테이블과 매핑 할 엔티티라고 JPA 에게 알려주는 역할을 하는 어노테이션
@Table(name = "board")
// 엔티티 클래스와 매핑되는 테이블 정보를 명시하는 어노테이션
public class BoardEntity extends TimeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private String writer;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Builder
    public BoardEntity(Long id, String title, String content, String writer) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
    }
}
