package com.serialgroup.serial.mapper;

import com.serialgroup.serial.model.UserTeam;
import com.serialgroup.serial.model.UserTeamExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserTeamMapper {
    long countByExample(UserTeamExample example);

    int deleteByExample(UserTeamExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserTeam row);

    int insertSelective(UserTeam row);

    List<UserTeam> selectByExample(UserTeamExample example);

    UserTeam selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") UserTeam row, @Param("example") UserTeamExample example);

    int updateByExample(@Param("row") UserTeam row, @Param("example") UserTeamExample example);

    int updateByPrimaryKeySelective(UserTeam row);

    int updateByPrimaryKey(UserTeam row);
}