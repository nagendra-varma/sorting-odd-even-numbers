package com.techo.sortdata.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/sortintegers")
public class SortingAlgorithmController {

    @GetMapping
    public ResponseEntity sortArray(@RequestParam(value = "array", defaultValue = "") String array) {
        Integer[] integers = convertStringToArray(array);
        if (integers.length == 0) {
            return badRequest().body("Input array should not be empty");
        }

        int leftIndex = 0;
        int rightIndex = integers.length - 1;

        while(leftIndex < rightIndex) {
            if (integers[leftIndex] % 2 == 0) {
                // Found even number on left side

                if (integers[rightIndex] % 2 != 0) {
                    // Odd number found on right side

                    // swap numbers
                    int temp = integers[leftIndex];
                    integers[leftIndex] = integers[rightIndex];
                    integers[rightIndex] = temp;
                }
                rightIndex--;
            } else {
                leftIndex++;
            }
        }
        return ok(integers);
    }

    private Integer[] convertStringToArray(String array) {
        String[] numberStrings = array.split(",");
        List<Integer> integers = new ArrayList<>();

        for(int index = 0; index < numberStrings.length ; index++) {
            if (!numberStrings[index].trim().isEmpty()) {
                integers.add(Integer.parseInt(numberStrings[index].trim()));
            }
        }
        return integers.toArray(new Integer[integers.size()]);
    }
}
