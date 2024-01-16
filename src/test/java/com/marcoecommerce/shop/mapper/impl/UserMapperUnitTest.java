package com.marcoecommerce.shop.mapper.impl;

import com.marcoecommerce.shop.config.MapperConfig;
import com.marcoecommerce.shop.model.order.OrderEntity;
import com.marcoecommerce.shop.utils.TestDataUtil;
import com.marcoecommerce.shop.mapper.Mapper;
import com.marcoecommerce.shop.model.user.UserDto;
import com.marcoecommerce.shop.model.user.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class UserMapperUnitTest {

    @Autowired
    private Mapper<UserEntity, UserDto> underTest;

    @Test
    public void givenUserEntity_whenMapToDto_thenSuccess() {
        // given
        UserEntity userAEntity = TestDataUtil.createUserEntityA();
        OrderEntity orderA = TestDataUtil.createOrderEntityA();
        OrderEntity orderB = TestDataUtil.createOrderEntityB();
        userAEntity.addOrder(orderA);
        userAEntity.addOrder(orderB);

        // when
        UserDto userDtoA = underTest.mapTo(userAEntity);

        // then
        assertEquals(userDtoA.getId(), userAEntity.getId());
        assertEquals(userDtoA.getEmail(), userAEntity.getEmail());
        assertEquals(userDtoA.getFirstName(), userAEntity.getFirstName());
        assertEquals(userDtoA.getLastName(), userAEntity.getLastName());
        assertEquals(userDtoA.getPassword(), userAEntity.getPassword());
        assertEquals(userDtoA.getPhone(), userAEntity.getPhone());

        assertEquals(userDtoA.getOrderList(), userAEntity.getOrderList());
    }

    @Test
    public void givenUserDto_whenMapToEntity_thenSuccess() {
        // given
        UserDto userDtoA = TestDataUtil.createUserDtoA();

        // when
        UserEntity userAEntity = underTest.mapFrom(userDtoA);

        // then
        assertEquals(userAEntity.getId(), userDtoA.getId());
        assertEquals(userAEntity.getEmail(), userDtoA.getEmail());
        assertEquals(userAEntity.getFirstName(), userDtoA.getFirstName());
        assertEquals(userAEntity.getLastName(), userDtoA.getLastName());
        assertEquals(userAEntity.getPassword(), userDtoA.getPassword());
        assertEquals(userAEntity.getPhone(), userDtoA.getPhone());

        assertEquals(userAEntity.getOrderList(), userDtoA.getOrderList());
    }

}
