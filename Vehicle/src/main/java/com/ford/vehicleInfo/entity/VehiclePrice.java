package com.ford.vehicleInfo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehiclePrice {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long priceId;
    private Double mSRP;
    private Double savings;
    private Double finalPrice;

    public String getMSRP() {
        return "$" + mSRP;
    }

    public String getFinalPrice() {
        return "$" + finalPrice;
    }

    public String getSavings() {
        return "$" + savings;
    }


}
