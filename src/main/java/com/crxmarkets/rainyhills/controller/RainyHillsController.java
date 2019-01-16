package com.crxmarkets.rainyhills.controller;

import com.crxmarkets.rainyhills.dto.RainResult;
import com.crxmarkets.rainyhills.service.RainyHillsSolver;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class RainyHillsController {

    private final RainyHillsSolver rainyHillsSolver;

    private static final int[] TASK_1_ARRAY = {3, 2, 4, 1, 2};
    private static final int[] TASK_2_ARRAY = {4, 1, 1, 0, 2, 3};

    @SneakyThrows
    @PostMapping("/rainWithFile")
    public String rainWithFile(@RequestParam("csvFile") MultipartFile csvFile, ModelMap modelMap) {
        RainResult rainResult = rainyHillsSolver.rain(csvFile);

        return setAndReturnResult(modelMap, rainResult);
    }

    @GetMapping("/rainWithTask1")
    public String rainWithTask1(ModelMap modelMap) {
        RainResult rainResult = rainyHillsSolver.rain(TASK_1_ARRAY);

        return setAndReturnResult(modelMap, rainResult);
    }

    @GetMapping("/rainWithTask2")
    public String rainWithTask2(ModelMap modelMap) {
        RainResult rainResult = rainyHillsSolver.rain(TASK_2_ARRAY);

        return setAndReturnResult(modelMap, rainResult);
    }

    private String setAndReturnResult(ModelMap modelMap, RainResult rainResult) {
        modelMap.addAttribute("rainResult", rainResult);

        return "rainResult";
    }

    @GetMapping("/welcome")
    public void welcomePage() {

    }
}