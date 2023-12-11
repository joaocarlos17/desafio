package com.salsatechnology.model;

import com.salsatechnology.dto.ProductOrderDTO;

public  class ProductOrderBuilder {
    public static ProductOrder buildFromDTO(ProductOrderDTO productOrderDTO) {
        return new ProductOrderBuilder()
                .userName(productOrderDTO.getUserName())
                .productType(productOrderDTO.getProductType())
                .timeHour(productOrderDTO.getTimeHour())
                .build();
    }

    private String userName;
    private ProductType productType;
    private int timeHour;

    public ProductOrderBuilder userName(String userName) {
        this.userName = userName;
        return this;
    }

    public ProductOrderBuilder productType(ProductType productType) {
        this.productType = productType;
        return this;
    }

    public ProductOrderBuilder timeHour(int timeHour) {
        this.timeHour = timeHour;
        return this;
    }

    public ProductOrder build() {
        ProductOrder productOrder = new ProductOrder();
        productOrder.setUserName(userName);
        productOrder.setProductType(productType);
        productOrder.setTimeHour(timeHour);
        return productOrder;
    }
}
