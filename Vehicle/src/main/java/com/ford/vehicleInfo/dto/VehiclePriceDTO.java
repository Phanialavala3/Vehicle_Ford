package com.ford.vehicleInfo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehiclePriceDTO {
    @JsonProperty("MSRP")
    private String mSRP;
    @JsonProperty("Savings")
    private String savings;
    private String finalPrice;
}
