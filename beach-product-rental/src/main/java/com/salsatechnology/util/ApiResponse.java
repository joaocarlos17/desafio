package com.salsatechnology.util;

import com.salsatechnology.model.ProductOrder;
import lombok.Data;

import java.util.List;

@Data
public class ApiResponse {
    private String message;
    private List<ProductOrder> data;

    public ApiResponse(String message, List<ProductOrder> data) {
        this.message = message;
        this.data = data;
    }
}

