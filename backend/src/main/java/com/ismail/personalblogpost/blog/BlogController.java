package com.ismail.personalblogpost.blog;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/blog")
public class BlogController {

    @GetMapping()
    public String hello() {
        return "hello there" ;
    }


}
