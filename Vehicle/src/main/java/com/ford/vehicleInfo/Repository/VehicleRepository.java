package com.ford.vehicleInfo.Repository;

import com.ford.vehicleInfo.entity.Vehicle;
import com.ford.vehicleInfo.entity.VehicleFeature;
import com.ford.vehicleInfo.entity.VehiclePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {

    List<Vehicle> findAllByModel(String model);

    List<Vehicle> findAllByVehiclePrice(List<VehiclePrice> vehiclePrices);

    List<Vehicle> findAllByVehicleFeatures_FeatureType_AndVehicleFeatures_FeatureContaining(List<VehicleFeature> vehicleFeatures);
}