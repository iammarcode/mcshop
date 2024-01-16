package com.marcoecommerce.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcoecommerce.shop.mapper.impl.UserMapper;
import com.marcoecommerce.shop.model.user.UserDto;
import com.marcoecommerce.shop.model.user.UserEntity;
import com.marcoecommerce.shop.service.UserService;
import com.marcoecommerce.shop.utils.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

@WebMvcTest(UserController.class)
public class UserControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper userMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private UserEntity userAEntity;
    private UserEntity userBEntity;
    private UserDto userADto;
    private UserDto userBDto;

    @BeforeEach
    public void setUp() {
        userAEntity = TestDataUtil.createUserEntityA();
        userBEntity = TestDataUtil.createUserEntityB();
        userADto = TestDataUtil.createUserDtoA();
        userBDto = TestDataUtil.createUserDtoB();
    }

    @Test
    public void givenUser_whenCallCreateUser_thenReturnJsonObject() throws Exception {
        // given
        Mockito.when(userMapper.mapFrom(userADto)).thenReturn(userAEntity);
        Mockito.when(userService.create(userAEntity)).thenReturn(userAEntity);
        Mockito.when(userMapper.mapTo(userAEntity)).thenReturn(userADto);

        // when
        // then
        String userJson = objectMapper.writeValueAsString(userADto);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.username").value(userAEntity.getUsername())
        );
    }

    @Test
    public void givenUsers_whenCallGetUsers_thenReturnJsonArray() throws Exception {
        Mockito.when(userService.findAll()).thenReturn(List.of(userAEntity, userBEntity));
        Mockito.when(userMapper.mapTo(userAEntity)).thenReturn(userADto);
        Mockito.when(userMapper.mapTo(userBEntity)).thenReturn(userBDto);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.length()").value(2)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].username").value(userAEntity.getUsername())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].username").value(userBEntity.getUsername())
        );
    }

    @Test
    public void givenUser_whenFindById_thenReturn200() throws Exception {
        // given
        Long userId = 1L;

        Mockito.when(userService.findById(userId)).thenReturn(Optional.of(userAEntity));
        Mockito.when(userMapper.mapTo(userAEntity)).thenReturn(userADto);

        // when
        // then
        String userDtoJson = objectMapper.writeValueAsString(userADto);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.username").value(userAEntity.getUsername())
        );
    }

    @Test
    public void givenNonexistentUser_whenUpdate_thenReturn404() throws Exception {
        Long nonexistentUserId = 1L;

        Mockito.when(userService.isExist(nonexistentUserId)).thenReturn(false);
        String userDtoJson = objectMapper.writeValueAsString(userADto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/users/" + nonexistentUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void givenUser_whenUpdate_thenReturn200() throws Exception {
        userADto.setId(1L);
        userAEntity.setId(1L);
        Mockito.when(userMapper.mapFrom(userADto)).thenReturn(userAEntity);
        Mockito.when(userService.update(userAEntity.getId(), userAEntity)).thenReturn(userAEntity);
        Mockito.when(userMapper.mapTo(userAEntity)).thenReturn(userADto);
        Mockito.when(userService.isExist(userADto.getId())).thenReturn(true);

        String userDtoJson = objectMapper.writeValueAsString(userADto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/users/" + userADto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.username").value(userAEntity.getUsername())
        );
    }

    @Test
    public void givenNoUser_whenCallDelete_thenReturn404() throws Exception {
        Long nonexistentUserId = 1L;
        Mockito.when(userService.isExist(nonexistentUserId)).thenReturn(false);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/" + nonexistentUserId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    public void givenUser_whenCallDelete_thenReturn204() throws Exception {
        Long existentUserId = 1L;
        Mockito.when(userService.isExist(existentUserId)).thenReturn(true);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/" + existentUserId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
