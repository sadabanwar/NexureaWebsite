package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseVideoDto {

    private Long id;

    @NotEmpty(message = "Title should not be empty")
    private String title;

    private String description;

    @NotEmpty(message = "Video URL should not be empty")
    private String videoUrl;

    private String thumbnailUrl;

    @NotNull(message = "Order index should not be null")
    private Integer orderIndex;

    @Positive(message = "Duration should be positive")
    private Integer durationMinutes;

    @NotNull(message = "Package ID should not be null")
    private Long packageId;
}
