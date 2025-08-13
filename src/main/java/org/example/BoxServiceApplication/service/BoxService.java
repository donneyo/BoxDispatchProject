package org.example.BoxServiceApplication.service;

import org.example.BoxServiceApplication.entity.Box;
import org.example.BoxServiceApplication.entity.BoxState;
import org.example.BoxServiceApplication.entity.Item;
import org.example.BoxServiceApplication.repository.BoxRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoxService {

    private final BoxRepository boxRepository;

    public BoxService(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }

    // Create a new box
    public Box createBox(Box box) {
        box.setItems(new ArrayList<>());
        return boxRepository.save(box);
    }

    // Load items into a specific box
    public Box loadItems(String boxId, List<Item> newItems) {
        Box box = boxRepository.findById(boxId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Box not found"));

        // Only allow loading if box is IDLE or LOADING
        if (!(box.getState() == BoxState.IDLE || box.getState() == BoxState.LOADING)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Box must be IDLE or LOADING to add items"
            );
        }

        //  Battery must be at least 25%
        if (box.getBatteryCapacity() < 25) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Battery too low for loading"
            );
        }

        //  Weight check (current + new items)
        double totalWeight = box.getItems().stream().mapToDouble(Item::getWeight).sum()
                + newItems.stream().mapToDouble(Item::getWeight).sum();

        if (totalWeight > box.getWeightLimit()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Weight limit exceeded"
            );
        }

        //  Add items and update state
        box.getItems().addAll(newItems);
        box.setState(BoxState.LOADING); // or LOADED depending on your workflow

        return boxRepository.save(box);
    }

    // Fetch all items in a box
    public List<Item> getLoadedItems(String boxId) {
        return boxRepository.findById(boxId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Box not found"))
                .getItems();
    }

    // Get available boxes (state = IDLE, battery ≥ 25%)
    public List<Box> getAvailableBoxes() {
        return boxRepository.findByStateAndBatteryCapacityGreaterThanEqual(BoxState.IDLE, 25);
    }

    // Get battery level for a box
    public int getBatteryLevel(String boxId) {
        return boxRepository.findById(boxId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Box not found"))
                .getBatteryCapacity();
    }
}
