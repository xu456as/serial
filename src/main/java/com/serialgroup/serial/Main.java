package com.serialgroup.serial;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().replace("-", "").length());
    }
}
