package com.ford.vehicleInfo;

import com.ford.vehicleInfo.dto.*;
import com.ford.vehicleInfo.entity.Vehicle;
import com.ford.vehicleInfo.entity.VehicleFeature;
import com.ford.vehicleInfo.entity.VehiclePrice;

import java.util.*;

public class TestUtil {
    public static Vehicle buildVehicleEntity() {
        return Vehicle.builder()
                .vehicleId("vehicleId")
                .model("Edge")
                .make("ford")
                .vehicleFeatures(new HashSet<>(Collections.singletonList(VehicleFeature.builder().feature("exterior1").featureType(FeatureType.EXTERIOR).build())))
                .vehiclePrice(new HashSet<>(Collections.singletonList(VehiclePrice.builder().finalPrice(10000D)
                        .mSRP(12000D)
                        .savings(2000D).build())))
                .build();
    }

    public static SubmitVehicleRequestDTO buildSubmitVehicleRequestDTO() {
        return SubmitVehicleRequestDTO.builder()
                .vehicles(VehiclesDTO.builder().vehicle(buildVehicleDTOList()).build())
                .build();
    }

    public static List<VehicleDTO> buildVehicleDTOList() {
        List<VehicleDTO> vehicleDTOS=new ArrayList<>();
        vehicleDTOS.add(VehicleDTO.builder()
                .vehicleId("vehicleId")
                .vehicleDetails(VehicleDetailsDTO.builder()
                        .model("Edge")
                        .make("ford")
                        .modelYear("2020")
                        .bodyStyle("")
                        .vehicleFeature(VehicleFeatureDTO.builder()
                                .exterior(Arrays.asList("exterior1", "exterior2", "exterior3"))
                                .interior(Arrays.asList("interior1", "interior2", "interior3"))
                                .build())
                        .vehiclePrice(Collections.singletonList(VehiclePriceDTO.builder()
                                .finalPrice("$10000")
                                .mSRP("$12000")
                                .savings("$2000")
                                .build()))
                        .build())
                .build());
        return vehicleDTOS;
    }
}
