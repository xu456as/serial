package com.serialgroup.serial.controller;

import com.serialgroup.serial.dto.AnonymityMatchDTO;
import com.serialgroup.serial.manager.FileManager;
import com.serialgroup.serial.manager.ImageMetaManager;
import com.serialgroup.serial.manager.UserInfoManager;
import com.serialgroup.serial.manager.VoiceRecognizeManager;
import com.serialgroup.serial.model.RelativesMeta;
import com.serialgroup.serial.util.AsrtUtil;
import com.serialgroup.serial.util.BdVoiceUtil;
import com.serialgroup.serial.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.net.NioChannel;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api/serial")
public class RelativesController {

    @Resource
    private ImageMetaManager relativesMetaManager;
    @Resource
    private UserInfoManager userInfoManager;

    @Resource
    private VoiceRecognizeManager voiceRecognizeManager;

    @Resource
    private FileManager fileManager;

    @PostMapping(path = "/image/list_all")
    public ResponseEntity<List<RelativesMeta>> listInfo(@RequestHeader String token,
                                                        @RequestHeader String groupId) {
        validate(token, groupId);
        List<RelativesMeta> relativesMetas = relativesMetaManager.listRelativesMetaByToken(groupId);
        if (!CollectionUtils.isEmpty(relativesMetas)) {
            for (RelativesMeta relativesMeta : relativesMetas) {
                if (relativesMeta.getIsCompleted()) {
                    continue;
                }
                relativesMeta.setName(String.format("%d号陌生人", relativesMeta.getAnonymousId()));
            }
        }
        return ResponseEntity.ok(relativesMetas);
    }

    @PostMapping(path = "/image/delete")
    public ResponseEntity<Integer> deleteInfo(@RequestHeader String token,
                                              @RequestHeader String groupId,
                                              @RequestParam String fileHash) {
        validate(token, groupId);
        int affectNum = relativesMetaManager.delete(groupId, fileHash);
        return ResponseEntity.ok(affectNum);
    }

    @RequestMapping(path = "/image/download")
    public ResponseEntity<byte[]> downloadImage(@RequestHeader(required = false) String token,
                                                @RequestHeader(required = false) String groupId,
                                                @RequestParam("fileHash") String fileHash) throws Exception {
//        validate(token, groupId);
        byte[] bytes = fileManager.download(fileHash);
        return ResponseEntity.ok(bytes);
    }

    @PostMapping(path = "/image/uploadAnonymous")
    public ResponseEntity<Integer> uploadAnonymous(@RequestHeader String token,
                                                   @RequestHeader String groupId,
                                                   @RequestParam("fileHash") String clientFileHash,
                                                   @RequestPart("image") MultipartFile file) throws Exception {
        validate(token, groupId);
        byte[] bytes = file.getBytes();
        String fileHash = DigestUtils.md5DigestAsHex(bytes);
        if (!clientFileHash.equals(fileHash)) {
            return ResponseEntity.badRequest().build();
        }
        fileManager.save(fileHash, bytes);
        int affectNum = relativesMetaManager.addAnonymous(groupId, fileHash);
        return ResponseEntity.ok(affectNum);
    }


    @PostMapping(path = "/image/updateByVoice")
    public ResponseEntity<Integer> updateByVoice(@RequestHeader String token,
                                                 @RequestHeader String groupId,
                                                 @RequestPart("voiceInput") MultipartFile voiceInput) throws Exception {
        validate(token, groupId);
        //todo
        AnonymityMatchDTO dto = AsrtUtil.INST.recognizeAnonymity(voiceInput.getBytes(), 16000, 1, 1);
        if (dto == null) {
            return ResponseEntity.ok(0);
        }
        String name = dto.getName();
        int id = dto.getAnonymousId();
        int result = relativesMetaManager.updateAnonymous(groupId, id, name);
        return ResponseEntity.ok(result);

//        String nickName = voiceRecognizeManager.recognizeName(voiceInput.getBytes());
//        if (nickName == null) {
//            return ResponseEntity.badRequest().build();
//        }
//        return null;
//        String fileHash = DigestUtils.md5DigestAsHex(file.getBytes());
//        fileManager.save(fileHash, file.getBytes());
//        int affectNum = imageMetaManager.addImage(groupId, fileHash, nickName);
//        return ResponseEntity.ok(affectNum);
    }

    @Deprecated
    @PostMapping(path = "/image/voiceRecognize")
    public ResponseEntity<Integer> uploadVoiceRecognize(@RequestHeader String token,
                                                        @RequestHeader String groupId,
                                                        @RequestPart("voiceInput") MultipartFile voiceInput,
                                                        @RequestPart("image") MultipartFile file
    ) throws Exception {
        validate(token, groupId);
        String nickName = voiceRecognizeManager.recognizeName(voiceInput.getBytes());
        if (nickName == null) {
            return ResponseEntity.badRequest().build();
        }
        String fileHash = DigestUtils.md5DigestAsHex(file.getBytes());
        fileManager.save(fileHash, file.getBytes());
        int affectNum = relativesMetaManager.addImage(groupId, fileHash, nickName);
        return ResponseEntity.ok(affectNum);
    }

    @PostMapping(path = "/image/recognizeAnonymousStg1")
    public ResponseEntity<AnonymityMatchDTO> recognizeAnonymousStg1(@RequestHeader String token,
                                                                    @RequestHeader String groupId,
                                                                    @RequestPart("voiceInput") MultipartFile file) throws Exception {
        validate(token, groupId);
        byte[] bytes = file.getBytes();
        AnonymityMatchDTO dto = BdVoiceUtil.INST.recognizeAnonymity(bytes, 16000, 1, 2);
        return ResponseEntity.ok(dto);
    }

    @PostMapping(path = "/image/recognizeAnonymousStg2")
    public ResponseEntity<Integer> recognizeAnonymousStg2(@RequestHeader String token,
                                                          @RequestHeader String groupId,
                                                          @RequestParam("name") String name,
                                                          @RequestParam("anonymousId") Integer anonymousId,
                                                          @RequestParam("ack") Boolean ack) throws Exception {
        validate(token, groupId);
        int affectNum = 0;
        if (ack) {
            affectNum = relativesMetaManager.updateAnonymous(groupId, anonymousId, name);
        }
        return ResponseEntity.ok(affectNum);
    }

    @RequestMapping("/image/uploadWx")
    @ResponseBody
    public ResponseEntity<Integer> uploadInfo(@RequestHeader String token,
                                              @RequestHeader String groupId,
                                              @RequestParam("nickName") String nickName,
                                              @RequestPart("image") MultipartFile file) throws Exception {
        validate(token, groupId);
        byte[] bytes = file.getBytes();
        String fileHash = DigestUtils.md5DigestAsHex(bytes);
        fileManager.save(fileHash, bytes);
        int affectNum = relativesMetaManager.addImage(groupId, fileHash, nickName);
        return ResponseEntity.ok(affectNum);
    }

    @RequestMapping("/image/updateWx")
    @ResponseBody
    public ResponseEntity<Integer> updateInfo(@RequestHeader String token,
                                              @RequestHeader String groupId,
                                              @RequestParam("id") Long id,
                                              @RequestParam(value = "nickName", required = false) String nickName,
                                              @RequestPart(value = "image", required = false) MultipartFile file) throws Exception {
        validate(token, groupId);
        byte[] bytes = null;
        String fileHash = null;
        RelativesMeta oldMeta = relativesMetaManager.getById(id);
        if (oldMeta == null) {
            return ResponseEntity.ok(0);
        }
        if (StringUtils.isBlank(nickName)) {
            nickName = null;
        }
        String name = Optional.ofNullable(nickName).orElse(getName(oldMeta));
        if (file != null) {
            bytes = file.getBytes();
            fileHash =  DigestUtils.md5DigestAsHex(bytes);
            fileManager.save(fileHash, bytes);
            int affectNum = relativesMetaManager.addImage(groupId, fileHash, name);
            affectNum += relativesMetaManager.deleteById(id);
            return ResponseEntity.ok(affectNum);
        } else {
            int affectNum = relativesMetaManager.update(id, name);
            return ResponseEntity.ok(affectNum);
        }
    }

    private String getName(RelativesMeta meta) {
        if (meta.getIsCompleted()) {
            return meta.getName();
        }
        return String.format("%d号陌生人", meta.getAnonymousId());
    }

    @RequestMapping("/image/uploadWx0")
    @ResponseBody
    public void uploadWx(HttpServletRequest request, HttpServletResponse response,
                         @RequestPart("image") MultipartFile imageFile) throws Exception {
        byte[] bytes = imageFile.getBytes();
        FileOutputStream fileOutputStream = new FileOutputStream("./baibaihe.jpeg");
        fileOutputStream.write(bytes, 0, bytes.length);
        fileOutputStream.flush();
        fileOutputStream.close();
        PrintWriter printWriter = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("success", true);
        printWriter.write(JsonUtil.getInstance().writeValueAsString(res));
        printWriter.flush();
        printWriter.close();
        //获取文件需要上传到的路径
//        String path = request.getRealPath("/upload") + "/";
//        File dir = new File(path);
//        if (!dir.exists()) {
//            dir.mkdir();
//        }
//        log.debug("path=" + path);
//
//        request.setCharacterEncoding("utf-8");  //设置编码
//        //获得磁盘文件条目工厂
//        DiskFileItemFactory factory = new DiskFileItemFactory();
//
//        //如果没以下两行设置的话,上传大的文件会占用很多内存，
//        //设置暂时存放的存储室,这个存储室可以和最终存储文件的目录不同
//        /**
//         * 原理: 它是先存到暂时存储室，然后再真正写到对应目录的硬盘上，
//         * 按理来说当上传一个文件时，其实是上传了两份，第一个是以 .tem 格式的
//         * 然后再将其真正写到对应目录的硬盘上
//         */
//        factory.setRepository(dir);
//        //设置缓存的大小，当上传文件的容量超过该缓存时，直接放到暂时存储室
//        factory.setSizeThreshold(1024 * 1024);
//        //高水平的API文件上传处理
//        ServletFileUpload upload = new ServletFileUpload(factory);
//        try {
//            List<FileItem> list = upload.parseRequest(new ServletRequestContext(request));
//            FileItem picture = null;
//            for (FileItem item : list) {
//                //获取表单的属性名字
//                String name = item.getFieldName();
//                //如果获取的表单信息是普通的 文本 信息
//                if (item.isFormField()) {
//                    //获取用户具体输入的字符串
//                    String value = item.getString();
//                    request.setAttribute(name, value);
//                    log.debug("name=" + name + ",value=" + value);
//                } else {
//                    picture = item;
//                }
//            }
//
//            //自定义上传图片的名字为userId.jpg
//            String fileName = request.getAttribute("userId") + ".jpg";
//            String destPath = path + fileName;
//            log.debug("destPath=" + destPath);
//
//            //真正写到磁盘上
//            File file = new File(destPath);
//            OutputStream out = new FileOutputStream(file);
//            InputStream in = picture.getInputStream();
//            int length = 0;
//            byte[] buf = new byte[1024];
//            // in.read(buf) 每次读到的数据存放在buf 数组中
//            while ((length = in.read(buf)) != -1) {
//                //在buf数组中取出数据写到（输出流）磁盘上
//                out.write(buf, 0, length);
//            }
//            in.close();
//            out.close();
//        } catch (FileUploadException e1) {
//            log.error("", e1);
//        } catch (Exception e) {
//            log.error("", e);
//        }
//
//
//        PrintWriter printWriter = response.getWriter();
//        response.setContentType("application/json");
//        response.setCharacterEncoding("utf-8");
//        HashMap<String, Object> res = new HashMap<String, Object>();
//        res.put("success", true);
//        printWriter.write(JsonUtil.getInstance().writeValueAsString(res));
//        printWriter.flush();
//        printWriter.close();
    }


    @PostMapping(path = "/login")
    public ResponseEntity<Boolean> login(@RequestHeader String token,
                                         @RequestHeader String groupId) {
        validate(token, groupId);
        return ResponseEntity.ok(true);
    }


    private void validate(String token, String groupId) {
        if (!userInfoManager.checkToken(groupId, token)) {
            throw new RuntimeException("Login error");
        }
    }
}
