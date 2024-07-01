package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write") //localhost:8080/board/write
    public String boardWriteForm() {

        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String boardwritepro(Board board) {

        boardService.write(board);


        return "redirect:/board/list";
    }

    @GetMapping("/board/list")
    public String boardlist(Model model) {

        model.addAttribute("list", boardService.boardlist());

        return "boardlist";
    }

    @GetMapping("board/view")
    public String boardView(Model model, @RequestParam(name = "id") Integer id)  {

        model.addAttribute("board", boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(@RequestParam("id")int id) {
        boardService.boardDelete(id);
        return "redirect:/board/list"; //게시물을 삭제하고 나면 통상적으로 게시물 목록으로 넘어간다.
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("board", boardService.boardView(id));

        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board) {

        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp);

        return "redirect:/board/list";

    }
}
