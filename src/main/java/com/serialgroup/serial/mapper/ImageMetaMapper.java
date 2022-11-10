package com.serialgroup.serial.mapper;

import com.serialgroup.serial.model.ImageMeta;
import com.serialgroup.serial.model.ImageMetaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImageMetaMapper {
    long countByExample(ImageMetaExample example);

    int deleteByExample(ImageMetaExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ImageMeta row);

    int insertSelective(ImageMeta row);

    List<ImageMeta> selectByExample(ImageMetaExample example);

    ImageMeta selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") ImageMeta row, @Param("example") ImageMetaExample example);

    int updateByExample(@Param("row") ImageMeta row, @Param("example") ImageMetaExample example);

    int updateByPrimaryKeySelective(ImageMeta row);

    int updateByPrimaryKey(ImageMeta row);
}