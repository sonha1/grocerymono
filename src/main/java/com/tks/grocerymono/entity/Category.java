package com.tks.grocerymono.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(nullable = false, length = 150, unique = true)
    private String code;

    @Nationalized
    @Column(nullable = false, length = 150)
    private String name;

    @Column
    private String imageUrl;

    @Column(name = "cloudinary_id", unique = true)
    private String cloudinaryId;

    @OneToMany(mappedBy = "category")
    private List<Product> productList;
}
