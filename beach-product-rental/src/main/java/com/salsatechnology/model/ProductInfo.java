package com.salsatechnology.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductInfo {
    private final double baseValue;
    private final double commissionPercentage;
}
