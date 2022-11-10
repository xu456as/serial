package com.serialgroup.serial.controller;

import com.serialgroup.serial.manager.ImageMetaManager;
import com.serialgroup.serial.manager.UserInfoManager;
import com.serialgroup.serial.model.ImageMeta;
import com.serialgroup.serial.vo.ImageMetaVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/serial")
public class SerialController {

    @Resource
    private ImageMetaManager imageMetaManager;
    @Resource
    private UserInfoManager userInfoManager;

    @PostMapping(path = "/image_meta/list_all")
    public ResponseEntity<List<ImageMeta>> listAll(@RequestHeader String token,
                                                     @RequestHeader String groupId) {
        validate(token, groupId);
        List<ImageMeta> imageMetas = imageMetaManager.listImageMetaByToken(groupId);
        return ResponseEntity.ok(imageMetas);
    }

    @PostMapping(path = "/image_meta/add")
    public ResponseEntity<List<ImageMeta>> addImage(@RequestHeader String token,
                                                    @RequestHeader String groupId,
                                                    @RequestParam String name,
                                                    @RequestParam MultipartFile file) {
        validate(token, groupId);
        List<ImageMeta> imageMetas = imageMetaManager.listImageMetaByToken(groupId);
        return ResponseEntity.ok(imageMetas);
    }

    @PostMapping(path = "/image_meta/delete")
    public ResponseEntity<List<ImageMeta>> deleteImage(@RequestHeader String token,
                                                    @RequestHeader String groupId,
                                                    @RequestParam String fileHash) {
        validate(token, groupId);
        List<ImageMeta> imageMetas = imageMetaManager.listImageMetaByToken(groupId);
        return ResponseEntity.ok(imageMetas);
    }

    @PostMapping(path = "/image/download")
    public ResponseEntity<List<ImageMetaVO>> downloadImage(@RequestHeader String token,
                                                           @RequestHeader String groupId,
                                                           @RequestParam("fileHash") String fileHash) {
        validate(token, groupId);
        return null;
    }


    @PostMapping(path = "/login")
    public ResponseEntity<List<ImageMetaVO>> login(@RequestHeader String token,
                                                           @RequestHeader String groupId) {
        validate(token, groupId);
        return null;
    }


    private void validate(String token, String groupId) {
        if (!userInfoManager.checkToken(groupId, token)) {
            throw new RuntimeException("Login error");
        }
    }
}
