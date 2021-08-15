package com.kmat100.bulletin_board1.controller;

import com.kmat100.bulletin_board1.dto.BoardDto;
import com.kmat100.bulletin_board1.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@AllArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/")
    public String list(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
//    @RequestParam 은 page 이름으로 넘어오면 파라미터를 받아주고, 없으면 기본 값으로 1을 설정
        List<BoardDto> boardList = boardService.getBoardlist(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);

        model.addAttribute("boardList", boardList);
        model.addAttribute("pageList", pageList);

        return "board/list";
    }





    @GetMapping("/post/{no}")
    public String detail(@PathVariable("no") Long no, Model model) {
        // 유동적으로 변하는 Path Variable을 처리하는 방법
        BoardDto boardDTO = boardService.getPost(no);
        model.addAttribute("boardDto", boardDTO);
        return "board/detail";
    }
    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model) {
        BoardDto boardDTO = boardService.getPost(no);
        model.addAttribute("boardDto", boardDTO);
        return "board/update";
    }
    @PutMapping("/post/edit/{no}")
    public String update(BoardDto boardDTO) {
        boardService.savePost(boardDTO);
        return "redirect:/";
    }
    @DeleteMapping("/post/{no}")
    public String delete(@PathVariable("no") Long no) {
        boardService.deletePost(no);
        return "redirect:/";
    }
    @GetMapping("/post")
    public String write() {
        return "board/write";
    }
    @PostMapping("/post")
    public String write(BoardDto boardDto) {
        boardService.savePost(boardDto);
        return "redirect:/";
    }
    @GetMapping("/board/search")
//    기존에 존재했던 컨트롤러에서 매핑함수 search() 를 작성, 클라이언트에서 넘겨주는 keyword 를 검색어로 활용
    public String search(@RequestParam(value="keyword") String keyword, Model model) {
        List<BoardDto> boardDtoList = boardService.searchPosts(keyword);

        model.addAttribute("boardList", boardDtoList);

        return "board/list";
    }
}

