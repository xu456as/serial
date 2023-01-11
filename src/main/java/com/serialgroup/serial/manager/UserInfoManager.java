package com.serialgroup.serial.manager;

import com.serialgroup.serial.mapper.UserTeamMapper;
import com.serialgroup.serial.model.UserTeam;
import com.serialgroup.serial.model.UserTeamExample;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class UserInfoManager {
    @Resource
    private UserTeamMapper userTeamMapper;
    public boolean checkToken(String groupId, String token) {
        UserTeamExample example = new UserTeamExample();
        example.createCriteria().andGroupIdEqualTo(groupId).andIsDeletedEqualTo(false);
        List<UserTeam> userTeams = userTeamMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(userTeams)) {
            return false;
        }
        UserTeam userTeam = userTeams.get(0);
        return StringUtils.equals(userTeam.getToken(), token);
    }
}
