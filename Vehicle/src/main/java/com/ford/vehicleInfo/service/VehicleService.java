package com.ford.vehicleInfo.service;


import com.ford.vehicleInfo.Repository.VehicleRepository;
import com.ford.vehicleInfo.dto.*;
import com.ford.vehicleInfo.entity.Vehicle;
import com.ford.vehicleInfo.entity.VehicleFeature;
import com.ford.vehicleInfo.entity.VehiclePrice;
import com.ford.vehicleInfo.exceptions.NoVehiclesFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final ModelMapper modelMapper;

    public VehicleService(VehicleRepository vehicleRepository, ModelMapper modelMapper) {
        this.vehicleRepository = vehicleRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public List<Vehicle> submitVehicle(SubmitVehicleRequestDTO submitVehicleRequestDTO) {
        List<Vehicle> vehicles = buildVehicles(submitVehicleRequestDTO.getVehicles());
        return vehicleRepository.saveAll(vehicles);
    }

    public VehiclesResponseDTO getVehicles() {
        return VehiclesResponseDTO.builder()
                .vehicles(buildVehiclesDTO(vehicleRepository.findAll()))
                .build();
    }

    public VehiclesResponseDTO getVehiclesByModel(String model) {
        return VehiclesResponseDTO.builder()
                .vehicles(buildVehiclesDTO(vehicleRepository.findAllByModel(model)))
                .build();
    }

    public VehiclesResponseDTO getVehiclesByPriceRange(Double fromValue, Double toValue) {
        return VehiclesResponseDTO.builder()
                .vehicles(buildVehiclesDTO(vehicleRepository.findByVehiclePrice_FinalPriceBetween(fromValue, toValue)))
                .build();
    }

    public VehiclesResponseDTO getVehiclesByMatchingExteriorInterior(String exterior, String interior) {
        List<Vehicle> exteriorMatchingVehicles = vehicleRepository.findByVehicleFeatures_FeatureTypeAndVehicleFeatures_Feature(FeatureType.EXTERIOR, exterior);
        List<Vehicle> interiorMatchingVehicles = vehicleRepository.findByVehicleFeatures_FeatureTypeAndVehicleFeatures_Feature(FeatureType.INTERIOR, interior);
        List<Vehicle> matchingVehicles = Stream.of(exteriorMatchingVehicles, interiorMatchingVehicles)
                .flatMap(x -> x.stream())
                .collect(Collectors.toList());
        Set<Vehicle> uniqueResults = new HashSet<>(matchingVehicles);

        return VehiclesResponseDTO.builder()
                .vehicles(buildVehiclesDTO(new ArrayList<>(uniqueResults)))
                .build();
    }

    private VehiclesDTO buildVehiclesDTO(List<Vehicle> vehicles) {
        List<VehicleDTO> vehicleDTOS = new ArrayList<>();
        if (vehicles != null && vehicles.size() > 0) {
            vehicles.forEach(vehicle -> {
                VehicleDetailsDTO vehicleDetailsDTO = modelMapper.map(vehicle, VehicleDetailsDTO.class);
                vehicleDetailsDTO.setVehicleFeature(buildVehicleFeatureDTO(vehicle.getVehicleFeatures()));
                vehicleDetailsDTO.setVehiclePrice(buildVehiclePriceDTO(vehicle.getVehiclePrice()));
                vehicleDTOS.add(VehicleDTO.builder()
                        .vehicleId(vehicle.getVehicleId())
                        .vehicleDetails(vehicleDetailsDTO)
                        .build());
            });
        }
        return VehiclesDTO.builder().vehicle(vehicleDTOS).build();
    }

    private List<Vehicle> buildVehicles(VehiclesDTO vehiclesDTO) {
        List<Vehicle> vehicles = new ArrayList<>();
        if (vehiclesDTO != null && vehiclesDTO.getVehicle() != null && vehiclesDTO.getVehicle().size() > 0) {
            vehiclesDTO.getVehicle().forEach(vehicleDTO -> {
                if (vehicleDTO.getVehicleDetails() != null) {
                    Vehicle vehicle = new Vehicle();
                    //Vehicle vehicle=modelMapper.map(vehicleDTO.getVehicleDetails(), Vehicle.class);
                    vehicle.setVehicleId(vehicleDTO.getVehicleId());

                    vehicle.setMake(vehicleDTO.getVehicleDetails().getMake());
                    vehicle.setModel(vehicleDTO.getVehicleDetails().getModel());
                    vehicle.setModelYear(vehicleDTO.getVehicleDetails().getModelYear());
                    vehicle.setBodyStyle(vehicleDTO.getVehicleDetails().getBodyStyle());
                    vehicle.setEngine(vehicleDTO.getVehicleDetails().getEngine());
                    vehicle.setDrivetype(vehicleDTO.getVehicleDetails().getDrivetype());
                    vehicle.setColor(vehicleDTO.getVehicleDetails().getColor());
                    vehicle.setMPG(vehicleDTO.getVehicleDetails().getMPG());

                    vehicle.setVehicleFeatures(buildVehicleFeatures(vehicleDTO.getVehicleDetails().getVehicleFeature()));
                    vehicle.setVehiclePrice(buildVehiclePrice(vehicleDTO.getVehicleDetails().getVehiclePrice()));
                    vehicles.add(vehicle);
                }
            });
        }
        return vehicles;
    }

    private Set<VehiclePrice> buildVehiclePrice(List<VehiclePriceDTO> vehiclePriceDTOS) {
        Set<VehiclePrice> vehiclePrices = new HashSet<>();
        if (vehiclePriceDTOS != null && vehiclePriceDTOS.size() > 0) {
            vehiclePriceDTOS.forEach(vehiclePriceDTO -> vehiclePrices.add(
                    VehiclePrice.builder()
                            .mSRP(convertVehiclePriceStringToDouble(vehiclePriceDTO.getMSRP()))
                            .finalPrice(convertVehiclePriceStringToDouble(vehiclePriceDTO.getFinalPrice()))
                            .savings(convertVehiclePriceStringToDouble(vehiclePriceDTO.getSavings()))
                            .build()
            ));
        }
        return vehiclePrices;
    }

    private Double convertVehiclePriceStringToDouble(String value) {
        Double finalValue;
        value = value.replace("$", "");
        value = value.replace(",", "");
        finalValue = Double.valueOf(value);
        return finalValue;
    }

    private List<VehiclePriceDTO> buildVehiclePriceDTO(Set<VehiclePrice> vehiclePriceS) {
        List<VehiclePriceDTO> vehiclePriceDTOS = new ArrayList<>();
        if (vehiclePriceS != null && vehiclePriceS.size() > 0) {
            vehiclePriceS.forEach(vehiclePrice -> vehiclePriceDTOS.add(
                    VehiclePriceDTO.builder()
                            .mSRP(vehiclePrice.getMSRP())
                            .finalPrice(vehiclePrice.getFinalPrice())
                            .savings(vehiclePrice.getSavings())
                            .build()
            ));
        }
        return vehiclePriceDTOS;
    }

    // this is while saving to DB for mapping DTO to entity
    private Set<VehicleFeature> buildVehicleFeatures(VehicleFeatureDTO vehicleFeature) {
        Set<VehicleFeature> vehicleFeatures = new HashSet<>();
        if (vehicleFeature != null && vehicleFeature.getExterior() != null && vehicleFeature.getExterior().size() > 0) {
            vehicleFeature.getExterior().forEach(feature -> vehicleFeatures.add(VehicleFeature.builder().featureType(FeatureType.EXTERIOR).feature(feature).build()));
        }
        if (vehicleFeature != null && vehicleFeature.getInterior() != null && vehicleFeature.getInterior().size() > 0) {
            vehicleFeature.getInterior().forEach(feature -> vehicleFeatures.add(VehicleFeature.builder().featureType(FeatureType.INTERIOR).feature(feature).build()));
        }
        return vehicleFeatures;
    }

    // this is while retrieving data from DB for mapping entity to DTO
    private VehicleFeatureDTO buildVehicleFeatureDTO(Set<VehicleFeature> vehicleFeatures) {
        VehicleFeatureDTO vehicleFeatureDTO = new VehicleFeatureDTO();
        List<String> exteriorArray = new ArrayList<>();
        List<String> interiorArray = new ArrayList<>();
        if (vehicleFeatures != null && vehicleFeatures.size() > 0) {
            for (VehicleFeature vehicleFeature : vehicleFeatures) {
                if (vehicleFeature.getFeatureType().equals(FeatureType.EXTERIOR)) {
                    exteriorArray.add(vehicleFeature.getFeature());
                }
            }
            vehicleFeatureDTO.setExterior(exteriorArray);
            for (VehicleFeature vehicleFeature : vehicleFeatures) {
                if (vehicleFeature.getFeatureType().equals(FeatureType.INTERIOR)) {
                    interiorArray.add(vehicleFeature.getFeature());
                }
            }
            vehicleFeatureDTO.setInterior(interiorArray);
        }
        return vehicleFeatureDTO;
    }


}

