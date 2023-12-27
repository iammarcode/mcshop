package com.marcoecommerce.shop.mapper;

// A: entity; B: dto
public interface Mapper<A, B> {
    public B mapTo(A t);
    public A mapFrom(B k);
}
