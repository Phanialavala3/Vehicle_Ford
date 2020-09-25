package com.ford.vehicleInfo.Repository;

import com.ford.vehicleInfo.dto.FeatureType;
import com.ford.vehicleInfo.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {

    List<Vehicle> findAllByModel(String model);

    List<Vehicle> findByVehicleFeatures_FeatureTypeAndVehicleFeatures_Feature(FeatureType featureType, String feature);

    List<Vehicle> findByVehiclePrice_FinalPriceBetween(Double minValue, Double maxValue);

}