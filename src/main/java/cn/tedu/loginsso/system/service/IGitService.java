package cn.tedu.loginsso.system.service;

import cn.tedu.loginsso.system.pojo.DTO.GitUserDTO;
import cn.tedu.loginsso.system.pojo.entity.GitUser;
import cn.tedu.loginsso.system.pojo.entity.UserAndGitee;
import cn.tedu.loginsso.system.pojo.vo.GitUserStandardVO;

/**
 * git用户业务层接口
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
public interface IGitService {

    void insert(GitUserDTO gitUserDTO);

    GitUser selectByLogin(String loginName);
}
