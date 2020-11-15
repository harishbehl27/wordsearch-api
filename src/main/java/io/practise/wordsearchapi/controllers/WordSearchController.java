package io.practise.wordsearchapi.controllers;


import io.practise.wordsearchapi.services.WordGridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController("/")
public class WordSearchController {

    @Autowired
    WordGridService wordGridService;


    @GetMapping("/wordgrid")
    @ResponseBody
    public String createWordGrid(@RequestParam int GridSize, @RequestParam String wordList)
    {

        List<String> words=Arrays.asList(wordList.split(","));
        char[][] grid=wordGridService.generateGrid(GridSize,words);
        String gridToString="";
        int gridSize=grid[0].length;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                gridToString+=grid[i][j];
            }

            gridToString+="\r\n";
        }
        return gridToString;
    }


}
