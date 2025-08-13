package org.example.BoxServiceApplication.controller;

import jakarta.validation.Valid;
import org.example.BoxServiceApplication.entity.Box;
import org.example.BoxServiceApplication.entity.Item;
import org.example.BoxServiceApplication.service.BoxService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boxes")
public class BoxController {

    private final BoxService boxService;

    public BoxController(BoxService boxService) {
        this.boxService = boxService;
    }

    // Create a box
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Box createBox(@Valid @RequestBody Box box) {
        return boxService.createBox(box);
    }

    // Add items to a specific box
    @PostMapping("/{boxId}/items")
    public Box loadItems(@PathVariable("boxId") String boxId,
                         @Valid @RequestBody List<Item> items) {
        return boxService.loadItems(boxId, items);
    }

    // Fetch all items loaded into a specific box
    @GetMapping("/{boxId}/items")
    public List<Item> getLoadedItems(@PathVariable("boxId") String boxId) {
        return boxService.getLoadedItems(boxId);
    }

    // Get all boxes that are available for loading (state = IDLE, battery ≥ 25%)
    @GetMapping("/available")
    public List<Box> getAvailableBoxes() {
        return boxService.getAvailableBoxes();
    }

    // Get the battery percentage of a box
    @GetMapping("/{boxId}/battery")
    public int getBatteryLevel(@PathVariable("boxId") String boxId) {
        return boxService.getBatteryLevel(boxId);
    }
}
