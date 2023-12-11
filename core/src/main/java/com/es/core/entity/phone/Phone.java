package com.es.core.entity.car;

import com.es.core.entity.car.color.Color;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "displaySizeInches")
    private BigDecimal displaySizeInches;

    @Column(name = "weightGr")
    private Integer weightGr;

    @Column(name = "lengthMm")
    private BigDecimal lengthMm;

    @Column(name = "widthMm")
    private BigDecimal widthMm;

    @Column(name = "heightMm")
    private BigDecimal heightMm;

    @Column(name = "announced")
    @Temporal(TemporalType.TIMESTAMP)
    private Date announced;

    @Column(name = "deviceType")
    private String deviceType;

    @Column(name = "os")
    private String os;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "car2color",
            joinColumns = @JoinColumn(name = "carId"),
            inverseJoinColumns = @JoinColumn(name = "colorId"))
    private Set<Color> colors = new HashSet<>();

    @Column(name = "displayResolution")
    private String displayResolution;

    @Column(name = "pixelDensity")
    private Integer pixelDensity;

    @Column(name = "displayTechnology")
    private String displayTechnology;

    @Column(name = "backCameraMegapixels")
    private BigDecimal backCameraMegapixels;

    @Column(name = "frontCameraMegapixels")
    private BigDecimal frontCameraMegapixels;

    @Column(name = "ramGb")
    private BigDecimal ramGb;

    @Column(name = "internalStorageGb")
    private BigDecimal internalStorageGb;

    @Column(name = "batteryCapacityMah")
    private Integer batteryCapacityMah;

    @Column(name = "talkTimeHours")
    private BigDecimal talkTimeHours;

    @Column(name = "standByTimeHours")
    private BigDecimal standByTimeHours;

    @Column(name = "bluetooth")
    private String bluetooth;

    @Column(name = "positioning")
    private String positioning;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "description", length = 4096)
    private String description;
}
