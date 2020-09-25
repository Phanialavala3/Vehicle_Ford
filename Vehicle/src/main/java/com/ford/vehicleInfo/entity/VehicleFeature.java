package com.ford.vehicleInfo.entity;

import com.ford.vehicleInfo.dto.FeatureType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleFeature {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long featureId;
    private FeatureType featureType;
    private String feature;
}
