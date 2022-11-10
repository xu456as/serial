package com.serialgroup.serial.manager;

import com.serialgroup.serial.mapper.ImageMetaMapper;
import com.serialgroup.serial.model.ImageMeta;
import com.serialgroup.serial.model.ImageMetaExample;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ImageMetaManager {

    @Resource
    private ImageMetaMapper imageMetaMapper;
    public List<ImageMeta> listImageMetaByToken(String groupId) {
        ImageMetaExample example = new ImageMetaExample();
        example.createCriteria().andGroupIdEqualTo(groupId);
        return imageMetaMapper.selectByExample(example);
    }
}
