package com.serialgroup.serial.mapper;

import com.serialgroup.serial.model.Test;
import com.serialgroup.serial.model.TestExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestMapper {
    long countByExample(TestExample example);

    int deleteByExample(TestExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Test row);

    int insertSelective(Test row);

    List<Test> selectByExample(TestExample example);

    int updateByExampleSelective(@Param("row") Test row, @Param("example") TestExample example);

    int updateByExample(@Param("row") Test row, @Param("example") TestExample example);
}