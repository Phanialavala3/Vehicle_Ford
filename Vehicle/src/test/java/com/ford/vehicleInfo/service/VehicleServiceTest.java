package com.ford.vehicleInfo.service;

import com.ford.vehicleInfo.Repository.VehicleRepository;
import com.ford.vehicleInfo.dto.FeatureType;
import com.ford.vehicleInfo.dto.SubmitVehicleRequestDTO;
import com.ford.vehicleInfo.dto.VehiclesResponseDTO;
import com.ford.vehicleInfo.entity.Vehicle;
import com.ford.vehicleInfo.entity.VehiclePrice;
import com.ford.vehicleInfo.exceptions.NoVehiclesFoundException;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ford.vehicleInfo.TestUtil.buildSubmitVehicleRequestDTO;
import static com.ford.vehicleInfo.TestUtil.buildVehicleEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    private VehicleService service;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeEach
    public void setup() {
        service = new VehicleService(vehicleRepository, new ModelMapper());
    }

    @Test
    void submitVehicles_should_save_multiple_vehicles_to_db() {
        //given
        SubmitVehicleRequestDTO submitVehicleRequestDTO = buildSubmitVehicleRequestDTO();

        //when
        when(service.submitVehicle(submitVehicleRequestDTO)).thenReturn(Collections.singletonList(buildVehicleEntity()));

        List<Vehicle> vehicles = service.submitVehicle(submitVehicleRequestDTO);

        //then
        assertEquals(1, vehicles.size());
    }

    @Test
    void getVehicleInformation_should_return_all_vehicles_from_db_repository() {

        //when
        when(vehicleRepository.findAll()).thenReturn(Collections.singletonList(buildVehicleEntity()));
        VehiclesResponseDTO vehiclesResponseDTO = service.getVehicles();
        //then
        assertEquals(1, vehiclesResponseDTO.getVehicles().getVehicle().size());


    }

    @Test
    void getVehicleInformation_should_return_exception_when_failure() {
        //given
        when(vehicleRepository.findAll()).thenReturn(null);
        VehiclesResponseDTO vehiclesResponseDTO = service.getVehicles();
        //then
        assertEquals(0, vehiclesResponseDTO.getVehicles().getVehicle().size());
    }

    @Test
    void getVehicleByModelName_should_return_all_vehicles_which_have_givenModel() {

        //when
        when(vehicleRepository.findAllByModel("Edge")).thenReturn(Collections.singletonList(buildVehicleEntity()));
        VehiclesResponseDTO vehiclesResponseDTO = service.getVehiclesByModel("Edge");
        //then
        assertEquals(1, vehiclesResponseDTO.getVehicles().getVehicle().size());


    }


    @Test
    void getVehiclesByMatchingExteriorInterior_should_return_vehicles_which_had_given_exterior_interior() {
        //given
        String exteriorFeature = "exterior";
        String interiorFeature = "interior";

        //when
        when(vehicleRepository.findByVehicleFeatures_FeatureTypeAndVehicleFeatures_Feature(FeatureType.EXTERIOR, exteriorFeature)).thenReturn(Collections.singletonList(buildVehicleEntity()));
        when(vehicleRepository.findByVehicleFeatures_FeatureTypeAndVehicleFeatures_Feature(FeatureType.INTERIOR, interiorFeature)).thenReturn(Collections.singletonList(buildVehicleEntity()));

        VehiclesResponseDTO vehiclesResponseDTO = service.getVehiclesByMatchingExteriorInterior(exteriorFeature, interiorFeature);
        //then
        assertEquals(1, vehiclesResponseDTO.getVehicles().getVehicle().size());
    }


    @Test
    void getVehiclesByPriceRange_should_return_vehicles_in_btw_price_range_from_db_as_response_dto() {
        //given
        Double minValue = 5000D;
        Double maxValue = 25000D;
        //when
        when(vehicleRepository.findByVehiclePrice_FinalPriceBetween(minValue, maxValue)).thenReturn(Collections.singletonList(buildVehicleEntity()));
        // when(vehicleRepository.findAllByVehiclePrice(vehiclePrices)).thenReturn(Collections.singletonList(buildVehicleEntity()));
        VehiclesResponseDTO vehiclesResponseDTO = service.getVehiclesByPriceRange(minValue, maxValue);
        //then
        assertEquals(1, vehiclesResponseDTO.getVehicles().getVehicle().size());
    }

}
