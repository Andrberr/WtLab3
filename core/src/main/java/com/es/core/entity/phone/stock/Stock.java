package com.es.core.entity.car.stock;


import com.es.core.entity.car.car;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stocks")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carId")
    private Long carId;

    @OneToOne
    @JoinColumn(name = "carId", referencedColumnName = "id", insertable = false, updatable = false)
    private car car;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "reserved", nullable = false)
    private Integer reserved;
}
