package com.example.restfulwebservice.controller;

import com.example.restfulwebservice.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void 모든_유저들을_가져온다() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/users"));

        resultActions
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void 특정_유저를_가져온다() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/users/1"));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andDo(print());
    }

//    @Test
//    public void 유저를_등록한다() throws Exception {
//        User user = new User();
//        String name = "dongwoo";
//        Date date = new Date();
//
//        user.setName(name);
//        user.setJoinDate(date);
//        String content = objectMapper.writeValueAsString(user);
//
//        ResultActions resultActions = mockMvc.perform(post("/users")
//                .content(content)
//                .contentType(MediaType.APPLICATION_JSON));
//
//        resultActions
//                .andExpect(status().isOk())
//                .andDo(print());
//    }

    @Test
    public void 상태코드_201을_확인한다() throws Exception {
        User user = new User();
        String name = "dongwoo";
        Date date = new Date();

        user.setName(name);
        user.setJoinDate(date);
        String content = objectMapper.writeValueAsString(user);

        ResultActions resultActions = mockMvc.perform(post("/users")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions
                .andExpect(status().isCreated())
                .andDo(print());
    }

//    @Test
//    public void 상태코드_500을_확인한다() throws UserNotFoundException, Exception {
//        ResultActions resultActions = mockMvc.perform(get("/users/100"));
//
//
//        resultActions
//                .andExpect(status().isInternalServerError())
//                .andDo(print());
//    }

    @Test
    public void 상태코드_404를_확인한다() throws Exception{
        ResultActions resultActions = mockMvc.perform(get("/users/100"));

        resultActions
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}