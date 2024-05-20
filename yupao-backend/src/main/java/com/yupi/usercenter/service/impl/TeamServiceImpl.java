package com.yupi.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.usercenter.model.domain.Team;
import com.yupi.usercenter.service.TeamService;
import com.yupi.usercenter.mapper.TeamMapper;
import org.springframework.stereotype.Service;

/**
* @author Li Changhe
* @description 针对表【team(队伍)】的数据库操作Service实现
* @createDate 2024-05-20 10:44:57
*/
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team>
    implements TeamService{

}




