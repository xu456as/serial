package com.serialgroup.serial.manager;

import com.serialgroup.serial.mapper.ImageMetaMapper;
import com.serialgroup.serial.model.ImageMeta;
import com.serialgroup.serial.model.ImageMetaExample;
import io.netty.util.internal.StringUtil;
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

    public int updateAnonymous(String groupId, int anonymousId, String nickName) {
        ImageMetaExample example = new ImageMetaExample();
        example.createCriteria().andGroupIdEqualTo(groupId)
                .andAnonymousIdEqualTo(anonymousId)
                .andIsCompletedEqualTo(false);
        ImageMeta imageMeta = new ImageMeta();
        imageMeta.setPriority(50);
        imageMeta.setAnonymousId(0);
        imageMeta.setIsCompleted(true);
        imageMeta.setName(nickName);
        return imageMetaMapper.updateByExampleSelective(imageMeta, example);
    }

    public int addAnonymous(String groupId, String fileHash) {
        int maxId = imageMetaMapper.selectMaxAnonymousId(groupId);
        ImageMeta imageMeta = new ImageMeta();
        imageMeta.setFileHash(fileHash);
        imageMeta.setGroupId(groupId);
        imageMeta.setName("");
        imageMeta.setPriority(0);
        imageMeta.setIsCompleted(false);
        imageMeta.setAnonymousId(maxId + 1);
        return imageMetaMapper.insertSelective(imageMeta);
    }
    public int addImage(String groupId, String fileHash, String nickName) {
        ImageMeta imageMeta = new ImageMeta();
        imageMeta.setFileHash(fileHash);
        imageMeta.setGroupId(groupId);
        imageMeta.setName(nickName);
        imageMeta.setPriority(50);
        imageMeta.setIsCompleted(true);
        imageMeta.setAnonymousId(0);
        return imageMetaMapper.insertSelective(imageMeta);
    }

    public int delete(String groupId, String fileHash) {
        ImageMetaExample example = new ImageMetaExample();
        example.createCriteria().andGroupIdEqualTo(groupId).andFileHashEqualTo(fileHash);
        return imageMetaMapper.deleteByExample(example);
    }
}
