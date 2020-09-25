package com.ford.vehicleInfo.Repository;

import com.ford.vehicleInfo.entity.VehiclePrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiclePriceRepository extends JpaRepository<VehiclePrice, Long> {
    List<VehiclePrice> findAllByFinalPriceBetween(Double minPrice, Double maxPrice);
}
