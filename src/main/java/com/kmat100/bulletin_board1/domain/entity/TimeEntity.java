package com.kmat100.bulletin_board1.domain.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
// 테이블로 매핑하지 않고, 자식 클래스(엔티티)에게 매핑정보를 상속하기 위한 어노테이션
@EntityListeners(AuditingEntityListener.class)
// JPA 에게 해당 Entity 는 Auditing 기능을 사용한다는 것을 알리는 어노테이션
public abstract class TimeEntity {
    @CreatedDate
    // 속성을 추가하지 않으면 수정 시, 해당 값은 null
    // Entity 가 처음 저장될때 생성일을 주입하는 어노테이션
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    // Entity 가 수정될때 수정일자를 주입하는 어노테이션
    private LocalDateTime modifiedDate;
}
