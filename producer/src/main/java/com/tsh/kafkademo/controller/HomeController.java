package com.tsh.kafkademo.controller;

import com.tsh.kafkademo.producer.KafkaSender;
import com.tsh.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {


    @Autowired
    private KafkaSender<Message> kafkaSender;

    @GetMapping("/send/{msg}")
    public String send(@PathVariable("msg") String msg) {

        Message message = new Message();
        message.setBody(msg);
        message.setFrom("producer");
        message.setTo("consumer");

        kafkaSender.send(message);
        return "success";
    }


    @GetMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name) {
        return "hello," + name;
    }
}
