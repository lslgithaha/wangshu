package com.wangshu.advanceprogrammer.common.upload;

import com.wangshu.advanceprogrammer.common.CommonMsg;
import com.wangshu.common.result.Result;
import com.wangshu.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Create by LSL on 2019\3\11 0011
 * 描述：
 * 版本：1.0.0
 */
@RestController
@RequestMapping("v1")
public class ImageController {

    @Value("${image.upload.basepath}")
    private String fileBasePath;

    @PostMapping("/image")
    public Result upload(@RequestParam MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw ResultUtil.conditionError(CommonMsg.IMAGENOTNULL);
        }
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        File dest = new File(fileBasePath + File.separator + uuid + suffix);
        try {
            file.transferTo(dest);
            return ResultUtil.getSuccessResult(uuid + suffix);
        } catch (IOException e) {
            throw e;
        }
    }
    @GetMapping("/image")
    public byte[] getImage(String imgid) throws IOException {
        File file = new File(fileBasePath + File.separator + imgid);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }
}
