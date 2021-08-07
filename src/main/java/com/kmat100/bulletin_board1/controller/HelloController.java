package com.kmat100.bulletin_board1.controller;

//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller // 1
//@RequestMapping("/board/**") // 1
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String Hello(){
//        return "/boards/hello"; //1
        return "Hello World!!!";
    }
}
