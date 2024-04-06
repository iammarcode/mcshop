package com.marco.mcshop.model.dto.shoppingCart;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marco.mcshop.model.dto.shoppingCartItem.ShoppingCartItemDto;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartDto {
    private Long id;

    private BigDecimal total;

    private List<ShoppingCartItemDto> itemList;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deletedAt;
}
