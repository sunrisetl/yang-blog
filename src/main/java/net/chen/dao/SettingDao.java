package net.chen.dao;

import net.chen.entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Chen
 * 2020/8/2 21:49
 */
public interface SettingDao extends JpaRepository<Settings,String>, JpaSpecificationExecutor<Settings> {

    Settings findOneByKey(String key);
}
