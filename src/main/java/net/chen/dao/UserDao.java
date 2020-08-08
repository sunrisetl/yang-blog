package net.chen.dao;

import net.chen.entity.ChenUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Chen
 * 2020/8/2 21:45
 */
public interface  UserDao extends JpaRepository<ChenUser,String>, JpaSpecificationExecutor<ChenUser> {

    ChenUser findOneByUsername(String username);

    ChenUser findOneByEmail(String email);
//
//    /**根据邮箱、用户名查找*/
//    List<ChenUser> findByOneEmailOrUserName(String userName,String email);

}
