package com.marcoecommerce.shop.service;

import com.marcoecommerce.shop.model.user.UserEntity;
import com.marcoecommerce.shop.repository.UserRepository;
import com.marcoecommerce.shop.service.impl.UserServiceImpl;
import com.marcoecommerce.shop.utils.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity userA;
    private UserEntity userB;

    @BeforeEach
    void setUp() {
        userA = TestDataUtil.createUserEntityA();
        userA.setId(1L);
        userB = TestDataUtil.createUserEntityB();
        userB.setId(2L);
    }

    @Test
    public void givenUser_whenSaveUser_thenReturnUser() {
        // given
        when(userRepository.save(userA)).thenReturn(userA);

        // when
        UserEntity userSaved = userService.create(userA);

        // then
        verify(userRepository, times(1)).save(any(UserEntity.class));
        assertEquals(userA, userSaved);
    }

    @Test
    public void givenUsers_whenFindAllUsers_thenReturnAllUsers() {
        // given
        when(userRepository.findById(userA.getId())).thenReturn(Optional.of(userA));

        // when
        Optional<UserEntity> userFound = userService.findById(userA.getId());

        // then
        verify(userRepository, times(1)).findById(any(Long.class));
        assertTrue(userFound.isPresent());
        assertEquals(userA, userFound.get());
    }

    @Test
    public void givenUser_whenFindById_thenReturnUser() {
        // given
        when(userRepository.findAll()).thenReturn(List.of(userA, userB));

        // when
        List<UserEntity> result = userService.findAll();

        // then
        verify(userRepository, times(1)).findAll();
        assertTrue(result.containsAll(List.of(userA, userB)));
    }

    @Test
    public void givenUser_whenIsExist_thenReturnTrue() {
        // given
        when(userRepository.existsById(userA.getId())).thenReturn(true);

        // when
        boolean result = userService.isExist(userA.getId());

        // then
        verify(userRepository, times(1)).existsById(any(Long.class));
        assertTrue(result);
    }

    @Test
    public void givenUser_whenDeleteById_thenSuccessful() {
        // given
        // when
        userService.deleteById(userA.getId());

        // then
        verify(userRepository, times(1)).deleteById(any(Long.class));
    }

    @Test
    public void givenUser_whenUpdateUser_thenReturnPartialUpdatedUser() {
        // given
        when(userRepository.findById(userA.getId())).thenReturn(Optional.of(userA));
        when(userRepository.save(userA)).thenReturn(userA);

        // when
        userA.setUsername("updated username");
        UserEntity userUpdated = userService.update(userA.getId(), userA);

        // then
        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(userRepository, times(1)).findById(any(Long.class));
        assertEquals("updated username", userUpdated.getUsername());
    }

}
