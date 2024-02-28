package com.marco.shop.mapper;

// A: entity; B: dto
public interface Mapper<A, B> {
    public B toDto(A t);
    public A toEntity(B k);


}
