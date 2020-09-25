package com.ford.vehicleInfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubmitVehicleResponseDTO {
    String status;
    Integer statusCode;
    String message;
}
