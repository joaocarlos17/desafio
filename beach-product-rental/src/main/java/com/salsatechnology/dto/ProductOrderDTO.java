package com.salsatechnology.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.salsatechnology.model.ProductType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.salsatechnology.util.Constant.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductOrderDTO {
	@NotBlank(message = FIELD_USERNAME_NOT_EMPTY)
	private String userName;

	@NotNull(message = FIELD_PRODUCTYPE_NOT_NULL)
	private ProductType productType;

	@NotNull(message = FIELD_TIMEHOUR_NOT_NULL)
	private Integer timeHour;
}
