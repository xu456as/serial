package com.serialgroup.serial.manager;

import com.serialgroup.serial.mapper.ImageMetaMapper;
import com.serialgroup.serial.model.RelativesMeta;
import com.serialgroup.serial.model.ImageMetaExample;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ImageMetaManager {

    @Resource
    private ImageMetaMapper imageMetaMapper;
    public List<RelativesMeta> listRelativesMetaByToken(String groupId) {
        ImageMetaExample example = new ImageMetaExample();
        example.createCriteria().andGroupIdEqualTo(groupId);
        return imageMetaMapper.selectByExample(example);
    }

    public int updateAnonymous(String groupId, int anonymousId, String nickName) {
        ImageMetaExample example = new ImageMetaExample();
        example.createCriteria().andGroupIdEqualTo(groupId)
                .andAnonymousIdEqualTo(anonymousId)
                .andIsCompletedEqualTo(false);
        RelativesMeta relativesMeta = new RelativesMeta();
        relativesMeta.setPriority(50);
        relativesMeta.setAnonymousId(0);
        relativesMeta.setIsCompleted(true);
        relativesMeta.setName(nickName);
        return imageMetaMapper.updateByExampleSelective(relativesMeta, example);
    }

    public int addAnonymous(String groupId, String fileHash) {
        int maxId = imageMetaMapper.selectMaxAnonymousId(groupId);
        RelativesMeta relativesMeta = new RelativesMeta();
        relativesMeta.setFileHash(fileHash);
        relativesMeta.setGroupId(groupId);
        relativesMeta.setName("");
        relativesMeta.setPriority(0);
        relativesMeta.setIsCompleted(false);
        relativesMeta.setAnonymousId(maxId + 1);
        return imageMetaMapper.insertSelective(relativesMeta);
    }
    public int addImage(String groupId, String fileHash, String nickName) {
        RelativesMeta relativesMeta = new RelativesMeta();
        relativesMeta.setFileHash(fileHash);
        relativesMeta.setGroupId(groupId);
        relativesMeta.setName(nickName);
        relativesMeta.setPriority(50);
        relativesMeta.setIsCompleted(true);
        relativesMeta.setAnonymousId(0);
        return imageMetaMapper.insertSelective(relativesMeta);
    }

    public RelativesMeta getById(Long id) {
        return imageMetaMapper.selectByPrimaryKey(id);
    }

    public int delete(String groupId, String fileHash) {
        ImageMetaExample example = new ImageMetaExample();
        example.createCriteria().andGroupIdEqualTo(groupId).andFileHashEqualTo(fileHash);
        return imageMetaMapper.deleteByExample(example);
    }

    public int deleteById(Long id) {
        return imageMetaMapper.deleteByPrimaryKey(id);
    }

    public int update(Long id, String nickName) {
        RelativesMeta meta = new RelativesMeta();
        meta.setName(nickName);
        meta.setIsCompleted(true);
        meta.setAnonymousId(0);
        ImageMetaExample example = new ImageMetaExample();
        example.createCriteria().andIdEqualTo(id);
        return imageMetaMapper.updateByExampleSelective(meta, example);
    }
}
