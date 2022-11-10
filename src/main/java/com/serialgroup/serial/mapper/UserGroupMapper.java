package com.serialgroup.serial.mapper;

import com.serialgroup.serial.model.UserGroup;
import com.serialgroup.serial.model.UserGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserGroupMapper {
    long countByExample(UserGroupExample example);

    int deleteByExample(UserGroupExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserGroup row);

    int insertSelective(UserGroup row);

    List<UserGroup> selectByExample(UserGroupExample example);

    UserGroup selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") UserGroup row, @Param("example") UserGroupExample example);

    int updateByExample(@Param("row") UserGroup row, @Param("example") UserGroupExample example);

    int updateByPrimaryKeySelective(UserGroup row);

    int updateByPrimaryKey(UserGroup row);
}