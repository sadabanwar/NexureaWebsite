package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursePackageDto {

    private Long id;

    @NotEmpty(message = "Package name should not be empty")
    private String name;

    @NotEmpty(message = "Description should not be empty")
    private String description;

    @NotNull(message = "Price should not be null")
    @Positive(message = "Price should be positive")
    private BigDecimal price;

    @NotNull(message = "Commission rate should not be null")
    private Double commissionRate;

    private Boolean active;

    private List<String> features;

    private String thumbnailUrl;

    private LocalDateTime createdAt;
}
