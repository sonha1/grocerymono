package com.tks.grocerymono.entity;

import com.tks.grocerymono.enums.BillStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "bill")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bill extends Base<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "staff_id")
    private String staffId;

    @Column(name = "total_price", columnDefinition = "INT CHECK (total_price > 0) NOT NULL")
    private Integer totalPrice;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private BillStatus status;

    @Column(name = "stripe_payment_id")
    private String stripePaymentId;

    @Column(name = "pick_up_time", nullable = false)
    private LocalDateTime pickUpTime;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.PERSIST)
    private List<BillItem> billItemList;
}
