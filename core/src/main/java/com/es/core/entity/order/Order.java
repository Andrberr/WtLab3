package com.es.core.entity.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "secureID")
    private String secureID;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @Column(name = "subtotal")
    private BigDecimal subtotal;

    @Column(name = "deliveryPrice")
    private BigDecimal deliveryPrice;

    @Column(name = "totalPrice")
    private BigDecimal totalPrice;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "deliveryAddress")
    private String deliveryAddress;

    @Column(name = "contactcarNo")
    private String contactcarNo;

    @Column(name = "additionalInformation", length = 4096)
    private String additionalInformation;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "time")
    private Time time;
}
