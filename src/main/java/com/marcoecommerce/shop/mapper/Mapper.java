package com.marcoecommerce.shop.mapper;

// A: entity; B: dto
public interface Mapper<A, B> {
    public B toResponse(A t);
    public A toEntity(B k);


}
