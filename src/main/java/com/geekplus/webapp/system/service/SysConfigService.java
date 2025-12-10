package com.geekplus.webapp.system.service;

import com.geekplus.webapp.system.entity.SysConfig;
//import com.geekplus.core.Service;
import java.util.List;


/**
 *
 * Created by CodeGenerator on 2023/06/18.
 */
public interface SysConfigService {

    /**
    * 增加
    * @param sysConfig
    * @return
    */
    public Integer insertSysConfig(SysConfig sysConfig);

    /**
    * 批量增加
    * @param sysConfigList
    * @return
    */
    public Integer batchInsertSysConfigList(List<SysConfig> sysConfigList);

    /**
    * 删除
    * @param configId
    */
    public Integer deleteSysConfigById(Long configId);

    /**
    * 批量删除某几个字段
    */
    public void deleteSysConfigByIds(Long[] configIds);

    /**
    * 修改
    * @param sysConfig
    */
    public Integer updateSysConfig(SysConfig sysConfig);

    /**
    * 批量修改
    * @param configIds
    */
    public Integer batchUpdateSysConfigList(Long[] configIds);

    /**
    * 查询全部
    */
    public List<SysConfig> selectSysConfigList(SysConfig sysConfig);

    /**
    * 查询全部，用作联合查询使用(在基础上修改即可)
    */
    public List<SysConfig> selectUnionSysConfigList(SysConfig sysConfig);

    /**
     * 加载参数缓存数据
     */
    public void loadingConfigCache();

    /**
     * 清空参数缓存数据
     */
    public void clearConfigCache();

    /**
     * 重置参数缓存数据
     */
    public void resetConfigCache();

    /**
    * 根据Id查询单条数据
    */
    public SysConfig selectSysConfigById(Long configId);

    /**
     * 根据key查询单条数据
     */
    public String selectSysConfigByKey(String configKey);

    /**
     * 获取验证码开关
     *
     * @return true开启，false关闭
     */
    public boolean selectCaptchaOnOff();

    /**
     * 校验参数键名是否唯一
     *
     * @param config 参数配置信息
     * @return 结果
     */
    public String checkConfigKeyUnique(SysConfig config);
}
