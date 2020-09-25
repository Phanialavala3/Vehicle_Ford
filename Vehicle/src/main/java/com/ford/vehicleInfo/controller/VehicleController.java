package com.ford.vehicleInfo.controller;

import com.ford.vehicleInfo.dto.ResponseForNotMatchedExteriorInteriorDTO;
import com.ford.vehicleInfo.dto.SubmitVehicleRequestDTO;
import com.ford.vehicleInfo.dto.SubmitVehicleResponseDTO;
import com.ford.vehicleInfo.dto.VehiclesResponseDTO;
import com.ford.vehicleInfo.entity.Vehicle;
import com.ford.vehicleInfo.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService=vehicleService;
    }

    @PostMapping("/vehicleInformation/submitVehicle")
    public SubmitVehicleResponseDTO submitVehicle(@RequestBody SubmitVehicleRequestDTO submitVehicleRequestDTO) {
        List<Vehicle> vehSet=vehicleService.submitVehicle(submitVehicleRequestDTO);


        return SubmitVehicleResponseDTO.builder()
                .message(vehSet.stream().map(Vehicle::getVehicleId).collect(Collectors.joining(",")) + " submitted to database successfully")
                .status(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    @GetMapping("/getVehicleInfomation")
    public VehiclesResponseDTO getVehicles() {
        return vehicleService.getVehicles();
    }

    @GetMapping("/getVehicleModelName/{modelName}")
    public VehiclesResponseDTO getVehiclesByModel(@PathVariable("modelName") String model) {
        return vehicleService.getVehiclesByModel(model);
    }

    @GetMapping("/getVehiclePrice/{From}/{TO}")
    public VehiclesResponseDTO getVehiclesByPriceRange(@PathVariable("From") Double fromValue, @PathVariable("TO") Double toValue) {
        return vehicleService.getVehiclesByPriceRange(fromValue, toValue);
    }

    @GetMapping("/getVehicleByFeatures/{exterior}/{interior}")
    public Object getVehiclesByMatchingExteriorInterior(@PathVariable("exterior") String exterior, @PathVariable("interior") String interior) {
        if (exterior.length() >= 3 && interior.length() >= 3) {
            VehiclesResponseDTO vehicles=new VehiclesResponseDTO();
            vehicles=vehicleService.getVehiclesByMatchingExteriorInterior(exterior, interior);
            if (vehicles == null) {
                ResponseForNotMatchedExteriorInteriorDTO response=new ResponseForNotMatchedExteriorInteriorDTO();
                response.setStatus("fail");
                response.setMessage("Error message");
                return response;
            } else
                return vehicles;
        } else {
            ResponseForNotMatchedExteriorInteriorDTO response=new ResponseForNotMatchedExteriorInteriorDTO();
            response.setStatus("fail");
            response.setMessage("Error message");
            return response;
        }

    }

}
