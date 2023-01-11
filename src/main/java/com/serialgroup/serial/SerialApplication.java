package com.serialgroup.serial;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.serialgroup.serial.mapper")
public class SerialApplication {

    public static void main(String[] args) {
        SpringApplication.run(SerialApplication.class, args);
    }

}
