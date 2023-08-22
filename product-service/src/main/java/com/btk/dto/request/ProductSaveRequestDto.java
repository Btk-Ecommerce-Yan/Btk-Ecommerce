package com.btk.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSaveRequestDto {
    private String productName;
    private Double price;
    private String description;
    private List<String> categoryIds;
    private String brandId;
    private List<String> photoImages;
}
