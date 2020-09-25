package com.ford.vehicleInfo.Repository;

import com.ford.vehicleInfo.dto.FeatureType;
import com.ford.vehicleInfo.entity.VehicleFeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleFeatureRepository extends JpaRepository<VehicleFeature, Long> {
    List<VehicleFeature> findByFeatureTypeAndFeatureIgnoreCase(FeatureType featureType, String feature);
}
