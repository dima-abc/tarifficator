package com.test2.product.entity;

import lombok.Getter;

@Getter
public enum TypeProduct {
    LOAN(1, "Кредит"), CARD(2, "Карта");
    private final int id;
    private final String name;

    TypeProduct(int id, String name) {
        this.id = id;
        this.name = name;
    }

}
