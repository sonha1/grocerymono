package com.tks.grocerymono.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bill_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(columnDefinition = "INT CHECK (price > 0) NOT NULL")
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bill_id")
    private Bill bill;
}
