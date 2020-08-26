package com.gpj.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：guopanjie001@ke.com
 * @description：
 * @date ：2020/8/26 2:06 下午
 */
@RestController
@Slf4j
public class HelloController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "hello world!";
    }
}
