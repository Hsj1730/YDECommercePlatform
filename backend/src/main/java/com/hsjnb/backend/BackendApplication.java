package com.hsjnb.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
        System.out.println(
                " ___  ___  ________        ___    _____  ________  ________  ________\n" +
                "|\\  \\|\\  \\|\\   ____\\      |\\  \\  / __  \\|\\_____  \\|\\_____  \\|\\   __  \\\n" +
                "\\ \\  \\\\\\  \\ \\  \\___|_     \\ \\  \\|\\/_|\\  \\\\|___/  /\\|____|\\ /\\ \\  \\|\\  \\\n" +
                " \\ \\   __  \\ \\_____  \\  __ \\ \\  \\|/ \\ \\  \\   /  / /     \\|\\  \\ \\  \\\\\\  \\\n" +
                "  \\ \\  \\ \\  \\|____|\\  \\|\\  \\\\_\\  \\   \\ \\  \\ /  / /     __\\_\\  \\ \\  \\\\\\  \\\n" +
                "   \\ \\__\\ \\__\\____\\_\\  \\ \\________\\   \\ \\__Y__/ /     |\\_______\\ \\_______\\\n" +
                "    \\|__|\\|__|\\_________\\|________|    \\|__|__|/      \\|_______|\\|_______|\n" +
                "             \\|_________|"
        );
    }

}
