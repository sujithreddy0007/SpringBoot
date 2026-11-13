package com.ecommerce.project.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    @Schema(description = "Category ID",example = "101")
    private Long categoryId;

    @Schema(description = "Category name for category you wish to access", example = "HMD vibe")
    private String categoryName;
}
