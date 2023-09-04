package com.serialgroup.serial.mapper;

import com.serialgroup.serial.model.RelativesMeta;
import com.serialgroup.serial.model.ImageMetaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImageMetaMapper {
    long countByExample(ImageMetaExample example);

    int deleteByExample(ImageMetaExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RelativesMeta row);

    int insertSelective(RelativesMeta row);

    List<RelativesMeta> selectByExample(ImageMetaExample example);

    RelativesMeta selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") RelativesMeta row, @Param("example") ImageMetaExample example);

    int updateByExample(@Param("row") RelativesMeta row, @Param("example") ImageMetaExample example);

    int updateByPrimaryKeySelective(RelativesMeta row);

    int updateByPrimaryKey(RelativesMeta row);

    Integer selectMaxAnonymousId(String groupId);
}