package io.practise.wordsearchapi.services;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class WordGridService {



        private class Coordinate {
            int x;
            int y;

            Coordinate(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }


        private enum Direction {
            HORIZONTAL,
            VERTICAL,
            DIAGONAL,
            HORIZONTAL_INVERSE,
            VERTICAL_INVERSE,
            DIAGONAL_INVERSE;

        }


        public char[][] generateGrid(int gridSize, List<String> words) {

            List<Coordinate> coordinates = new ArrayList<>();
            char[][] contents = new char[gridSize][gridSize];
            for (int i = 0; i < gridSize; i++) {
                for (int j = 0; j < gridSize; j++) {
                    coordinates.add(new Coordinate(i, j));
                    contents[i][j] = '_';
                }
            }
            Collections.shuffle(coordinates);
            for (String word : words) {

                for (Coordinate coordinate : coordinates) {
                    int x = coordinate.x;
                    int y = coordinate.y;

                    Direction selectedDirection = getDirectionForFit(contents, word, coordinate);

                    if (selectedDirection != null) {

                        switch (selectedDirection) {
                            case HORIZONTAL:
                                for (char c : word.toCharArray()) {
                                    contents[x][y++] = c;
                                }
                                break;

                            case VERTICAL:
                                for (char c : word.toCharArray()) {
                                    contents[x++][y] = c;
                                }

                                break;
                            case DIAGONAL: {
                                for (char c : word.toCharArray()) {
                                    contents[x++][y++] = c;
                                }
                            }
                            break;

                            case HORIZONTAL_INVERSE:
                                for (char c : word.toCharArray()) {
                                    contents[x][y--] = c;
                                }
                                break;

                            case VERTICAL_INVERSE:
                                for (char c : word.toCharArray()) {
                                    contents[x--][y] = c;
                                }

                                break;
                            case DIAGONAL_INVERSE: {
                                for (char c : word.toCharArray()) {
                                    contents[x--][y--] = c;
                                }
                            }
                            break;
                        }

                        break;

                    }

                }

            }
            randomFillGrid(contents);

            return contents;

        }

        public void displayGrid(char[][] contents) {

            int gridSize=contents[0].length;
            for (int i = 0; i < gridSize; i++) {
                for (int j = 0; j < gridSize; j++) {
                    System.out.print(contents[i][j] + " ");
                }

                System.out.println("");
            }
        }

        private Direction getDirectionForFit(char[][] contents, String word, Coordinate coordinate) {

            List<Direction> directions = Arrays.asList(Direction.values());
            Collections.shuffle(directions);

            for (Direction direction : directions) {


                if (doesFit(contents, word, coordinate, direction)) {
                    return direction;
                }

            }
            return null;
        }

        private boolean doesFit(char[][] contents, String word, Coordinate coordinate, Direction direction) {

            int wordLength = word.length();
            int gridSize=contents[0].length;
            switch (direction) {
                case DIAGONAL:
                    if ((coordinate.x + word.length() > gridSize) || (coordinate.y + word.length() > gridSize)) {
                        return false;
                    }
                    for (int i = 0; i < wordLength; i++) {
                        if (contents[coordinate.x + i][coordinate.y + i] != '_')
                            return false;
                    }

                    break;

                case VERTICAL:
                    if (coordinate.x + word.length() > gridSize) {
                        return false;
                    }

                    for (int i = 0; i < wordLength; i++) {
                        if (contents[coordinate.x + i][coordinate.y] != '_')
                            return false;
                    }

                    break;

                case HORIZONTAL:

                    if (coordinate.y + word.length() > gridSize) {
                        return false;
                    }

                    for (int i = 0; i < wordLength; i++) {
                        if (contents[coordinate.x][coordinate.y + i] != '_')
                            return false;
                    }
                    break;

                case DIAGONAL_INVERSE:
                    if ((coordinate.x < word.length()) || (coordinate.y < word.length())) {
                        return false;
                    }
                    for (int i = 0; i < wordLength; i++) {
                        if (contents[coordinate.x - i][coordinate.y - i] != '_')
                            return false;
                    }

                    break;

                case VERTICAL_INVERSE:
                    if (coordinate.x < word.length()) {
                        return false;
                    }

                    for (int i = 0; i < wordLength; i++) {
                        if (contents[coordinate.x - i][coordinate.y] != '_')
                            return false;
                    }

                    break;

                case HORIZONTAL_INVERSE:

                    if (coordinate.y < word.length()) {
                        return false;
                    }

                    for (int i = 0; i < wordLength; i++) {
                        if (contents[coordinate.x][coordinate.y - i] != '_')
                            return false;
                    }
                    break;

            }
            return true;
        }


        private void randomFillGrid(char[][] contents)
        {
            int gridSize=contents[0].length;
            String allCapLetters="ABCDEFGHIJKLMNOPQRSTUVWXYZ";


            for (int i = 0; i < gridSize; i++) {
                for (int j = 0; j < gridSize; j++) {
                    if(contents[i][j] == '_')
                    {
                        int index= ThreadLocalRandom.current().nextInt(0,allCapLetters.length());
                        contents[i][j]=allCapLetters.charAt(index);
                    }
                }

            }

        }
    }





