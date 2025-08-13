package org.example.BoxServiceApplication.entity;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "Name can only contain letters, numbers, hyphen (-), and underscore (_)")
    private String name;

    @Positive(message = "Weight must be greater than 0")
    private int weight; // grams

    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Code can only contain uppercase letters, numbers, and underscore (_)")
    private String code;
}
