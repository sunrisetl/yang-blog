package net.chen.dao;

import net.chen.entity.Labels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Chen
 * 2020/8/3 20:56
 */
public interface LabelDao extends JpaRepository<Labels,String>, JpaSpecificationExecutor<Labels> {
    @Modifying
    int deleteOneById(int id);

    @Modifying
    @Query(value = "delete * from tb_labels where id =?",nativeQuery = true)
    int customDeleteById(String id);

    /** 根据id查找标签*/
    Labels findOneById(String id);
}
