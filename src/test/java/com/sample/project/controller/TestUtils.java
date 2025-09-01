package com.sample.project.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestUtils {
    public static String loadJsonFromResource(String fileName) throws IOException {
        return new String(
            Files.readAllBytes(
                Paths.get(ClassLoader.getSystemResource(fileName).getPath())
            )
        );
    }
}