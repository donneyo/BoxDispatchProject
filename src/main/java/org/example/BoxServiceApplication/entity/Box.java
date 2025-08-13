package org.example.BoxServiceApplication.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "boxes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Box {

    @Id
    private String id;

    @NotBlank(message = "txref is required")
    @Size(max = 20, message = "txref must not exceed 20 characters")
    private String txref;

    @Max(value = 500, message = "Weight limit cannot exceed 500 grams")
    @Positive(message = "Weight limit must be positive")
    private int weightLimit; // grams

    @Min(value = 0, message = "Battery capacity cannot be less than 0")
    @Max(value = 100, message = "Battery capacity cannot exceed 100%")
    private int batteryCapacity; // percentage

    @NotNull(message = "State is required")
    private BoxState state; // Enum

    private List<Item> items;
}
