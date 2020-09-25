package com.ford.vehicleInfo.repository;

import com.ford.vehicleInfo.Repository.VehicleRepository;
import com.ford.vehicleInfo.dto.FeatureType;
import com.ford.vehicleInfo.entity.Vehicle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.ford.vehicleInfo.TestUtil.buildVehicleEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VehicleRepositoryTest {
    @Autowired
    VehicleRepository vehicleRepository;

    @Test
    public void findAllByModel_should_return_matching_vehicles_from_db() {
        vehicleRepository.deleteAll();
        Vehicle vehicle = buildVehicleEntity();
        vehicleRepository.save(vehicle);

        List<Vehicle> vehicles = vehicleRepository.findAllByModel("Edge");
        assertEquals(1, vehicles.size());
        assertEquals("Edge", vehicles.get(0).getModel());
    }

    @Test
    public void findAllByVehicleFeatures_should_return_matching_vehicles_from_db(){
        vehicleRepository.deleteAll();
        Vehicle vehicle = buildVehicleEntity();
        vehicleRepository.save(vehicle);
        List<Vehicle> vehicles = vehicleRepository.findByVehicleFeatures_FeatureTypeAndVehicleFeatures_Feature(FeatureType.EXTERIOR, "exterior1");
        assertEquals(1, vehicles.size());
        assertTrue(vehicles.get(0).getVehicleFeatures().stream().filter(vehicleFeature -> FeatureType.EXTERIOR.equals(vehicleFeature.getFeatureType())).findFirst().isPresent());
        assertEquals("exterior1", vehicles.get(0).getVehicleFeatures().stream().filter(vehicleFeature -> FeatureType.EXTERIOR.equals(vehicleFeature.getFeatureType())).findFirst().get().getFeature());
    }

    @Test
    public void findByVehiclePrice_FinalPriceBetween_should_return_vechiles_btw_prices() {
        vehicleRepository.deleteAll();
        Vehicle vehicle=buildVehicleEntity();
        vehicleRepository.save(vehicle);
        List<Vehicle> vehicles=vehicleRepository.findByVehiclePrice_FinalPriceBetween(5000D, 15000D);
        assertEquals(1, vehicles.size());
    }
}
