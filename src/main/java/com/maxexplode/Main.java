package com.maxexplode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try (InputStream is = Main.class.getResourceAsStream("/banner.txt")) {
            if (is == null) {
                log.error("Could not find banner.txt on the classpath");
                return;
            }
            String banner = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining(System.lineSeparator()));

            log.info("\n{}", banner);
        } catch (Exception e) {
            log.error("Failed to load or print banner.txt", e);
        }
    }
}
