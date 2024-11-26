package com.tks.grocerymono.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "stripe_customer", uniqueConstraints = {
        @UniqueConstraint(name = "uq_stripe_customer_id_user_id", columnNames = {"stripe_customer_id", "user_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StripeCustomer extends Base<String> {
    @Id
    @Column(name = "stripe_customer_id")
    private String stripeCustomerId;
    @Column(name = "user_id", nullable = false)
    private String userId;
}
