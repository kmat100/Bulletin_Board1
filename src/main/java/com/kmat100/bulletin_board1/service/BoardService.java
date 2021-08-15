package com.kmat100.bulletin_board1.service;

import com.kmat100.bulletin_board1.domain.entity.BoardEntity;
import com.kmat100.bulletin_board1.dto.BoardDto;
import com.kmat100.bulletin_board1.domain.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BoardService {
    private BoardRepository boardRepository;
    private static final int BLOCK_PAGE_NUM_COUNT = 10; // 블럭에 존재하는 페이지 번호 수
    private static final int PAGE_POST_COUNT =  10; // 한페이지에 존재하는 게시글 수

    @Transactional
    public List<BoardDto> getBoardlist(Integer pageNum) {
        Page<BoardEntity> page = boardRepository.findAll(PageRequest.of(pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.ASC, "createdDate")));
//      repository 의 find() 관련 메서드를 호출할 때 Pageable 인터페이스를 구현한 클래스(PageRequest.of())를 전달하면 페이징을 할 수 있다.
        List<BoardEntity> boardEntities = page.getContent();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for (BoardEntity boardEntity : boardEntities) {
            boardDtoList.add(this.convertEntityToDto(boardEntity));
        }

        return boardDtoList;
    }
//    public List<BoardDto> getBoardlist() {
//        List<BoardEntity> boardEntities = boardRepository.findAll();
//        List<BoardDto> boardDtoList = new ArrayList<>();
//
//        for ( BoardEntity boardEntity : boardEntities) {
//            BoardDto boardDTO = BoardDto.builder()
//                    .id(boardEntity.getId())
//                    .title(boardEntity.getTitle())
//                    .content(boardEntity.getContent())
//                    .writer(boardEntity.getWriter())
//                    .createdDate(boardEntity.getCreatedDate())
//                    .build();
//
//            boardDtoList.add(boardDTO);
//        }
//        return boardDtoList;
//    }
public Long getBoardCount() {
//    전체 게시글 개수를 가져온다.
    return boardRepository.count();
}

    public Integer[] getPageList(Integer curPageNum) {
//        프론트에 노출시킬 페이지 번호 리스트를 계산하는 로직
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

// 총 게시글 갯수
        Double postsTotalCount = Double.valueOf(this.getBoardCount());

// 총 게시글 기준으로 계산한 마지막 페이지 번호 계산 (올림으로 계산)
        Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));

// 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;

// 페이지 시작 번호 조정
        curPageNum = (curPageNum <= 3) ? 1 : curPageNum - 2;

// 페이지 번호 할당
        for (int val = curPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
            pageList[idx] = val;
        }

        return pageList;
    }
    @Transactional
    public BoardDto getPost(Long id) {
        Optional<BoardEntity> boardEntityWrapper = boardRepository.findById(id);
//        PK 값을 where 조건으로 하여, 데이터를 가져오기 위한 메서드이며, JpaRepository 인터페이스에서 정의
//        반환 값은 Optional 타입인데, 엔티티를 쏙 빼오려면 boardEntityWrapper.get(); 이렇게 get() 메서드를 사용해 가져
        BoardEntity boardEntity = boardEntityWrapper.get();

        BoardDto boardDTO = BoardDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .writer(boardEntity.getWriter())
                .createdDate(boardEntity.getCreatedDate())
                .build();

        return boardDTO;
    }
    // ? 1
    @Transactional
    public Long savePost(BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getId();
        //JpaRepository 에 정의된 메서드로, DB에 INSERT, UPDATE를 담당
    }

    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }
    @Transactional
    public List<BoardDto> searchPosts(String keyword) {
//        Repository에서 검색 결과를 받아와 비즈니스 로직을 실행하는 함수
//        Controller <---> Service 간에는 Dto 객체로 전달하는 것이 좋으므로 이와 관련된 로직
        List<BoardEntity> boardEntities = boardRepository.findByTitleContaining(keyword);
        List<BoardDto> boardDtoList = new ArrayList<>();

        if (boardEntities.isEmpty()) return boardDtoList;

        for (BoardEntity boardEntity : boardEntities) {
            boardDtoList.add(this.convertEntityToDto(boardEntity));
        }

        return boardDtoList;
    }

    private BoardDto convertEntityToDto(BoardEntity boardEntity) {
//     Entity 를 Dto 로 변환하는 작업이 중복해서 발생했었는데, 이를 함수로 처리하도록 개선
        return BoardDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .writer(boardEntity.getWriter())
                .createdDate(boardEntity.getCreatedDate())
                .build();
    }
}





