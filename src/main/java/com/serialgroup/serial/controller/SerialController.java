package com.serialgroup.serial.controller;

import com.serialgroup.serial.vo.ImageMetaVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/serial")
public class SerialController {
//    TODO

    @PostMapping(path = "/image_meta/list_all")
    public ResponseEntity<List<ImageMetaVO>> listAll(@RequestHeader String token) {

        return null;
    }

    @PostMapping(path = "/image/download")
    public ResponseEntity<List<ImageMetaVO>> downloadImage(@RequestHeader String token,
                                                           @RequestParam("fileHash") String fileHash) {

        return null;
    }

}