//package com.ford.vehicleInfo.repository;
//
//import com.ford.vehicleInfo.Repository.VehicleRepository;
//import com.ford.vehicleInfo.entity.Vehicle;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.List;
//
//import static com.ford.vehicleInfo.TestUtil.buildVehicleEntity;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@DataJpaTest
//public class VehicleRepositoryTest {
//    @Autowired
//    VehicleRepository vehicleRepository;
//
//
//    public void findAllByModel_should_return_matching_vehicles_from_db() {
//        Vehicle vehicle=buildVehicleEntity();
//        vehicleRepository.save(vehicle);
//
//        List<Vehicle> vehicles=vehicleRepository.findAllByModel("Edge");
//        assertTrue(vehicles.contains(vehicle));
//    }
//}
