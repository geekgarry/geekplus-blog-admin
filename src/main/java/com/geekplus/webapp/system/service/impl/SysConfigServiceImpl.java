package com.geekplus.webapp.system.service.impl;

import com.geekplus.common.constant.Constant;
import com.geekplus.common.constant.UserConstants;
import com.geekplus.common.core.text.Convert;
import com.geekplus.common.redis.RedisUtil;
import com.geekplus.common.util.string.StringUtils;
import com.geekplus.framework.web.exception.BusinessException;
import com.geekplus.webapp.system.mapper.SysConfigMapper;
import com.geekplus.webapp.system.entity.SysConfig;
import com.geekplus.webapp.system.service.SysConfigService;
//import com.geekplus.core.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2023/06/18.
 */
@Service
@Transactional
public class SysConfigServiceImpl implements SysConfigService {
    @Resource
    private SysConfigMapper sysConfigMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 项目启动时，初始化参数到缓存
     */
    @PostConstruct
    public void init()
    {
        loadingConfigCache();
    }

    /**
    * 增加
    * @param sysConfig
    * @return
    */
    public Integer insertSysConfig(SysConfig sysConfig){
        int row = sysConfigMapper.insertSysConfig(sysConfig);
        if (row > 0)
        {
            redisUtil.setCacheObject(getCacheKey(sysConfig.getConfigKey()), sysConfig.getConfigValue());
        }
        return row;
    }

    /**
    * 批量增加
    * @param sysConfigList
    * @return
    */
    public Integer batchInsertSysConfigList(List<SysConfig> sysConfigList){
        return sysConfigMapper.batchInsertSysConfigList(sysConfigList);
    }

    /**
    * 删除
    * @param configId
    */
    public Integer deleteSysConfigById(Long configId){
        return sysConfigMapper.deleteSysConfigById(configId);
    }

    /**
    * 批量删除
    */
    public void deleteSysConfigByIds(Long[] configIds){
        for (Long configId : configIds)
        {
            SysConfig config = selectSysConfigById(configId);
            if (StringUtils.equals(UserConstants.YES, config.getConfigType()))
            {
                throw new BusinessException(String.format("内置参数【%1$s】不能删除 ", config.getConfigKey()));
            }
            sysConfigMapper.deleteSysConfigById(configId);
            redisUtil.del(getCacheKey(config.getConfigKey()));
        }
    }

    /**
    * 修改
    * @param sysConfig
    */
    public Integer updateSysConfig(SysConfig sysConfig){
        int row = sysConfigMapper.updateSysConfig(sysConfig);
        if (row > 0)
        {
            redisUtil.setCacheObject(getCacheKey(sysConfig.getConfigKey()), sysConfig.getConfigValue());
        }
        return row;
    }

    /**
    * 批量修改某几个字段
    * @param configIds
    */
    public Integer batchUpdateSysConfigList(Long[] configIds){
        return sysConfigMapper.batchUpdateSysConfigList(configIds);
    }

    /**
    * 查询全部
    */
    public List<SysConfig> selectSysConfigList(SysConfig sysConfig){
        return sysConfigMapper.selectSysConfigList(sysConfig);
    }

    /**
    * 查询全部,用于联合查询，在此基础做自己的定制改动
    */
    public List<SysConfig> selectUnionSysConfigList(SysConfig sysConfig){
        return sysConfigMapper.selectUnionSysConfigList(sysConfig);
    }

    /**
     * 加载参数缓存数据
     */
    @Override
    public void loadingConfigCache()
    {
        List<SysConfig> configsList = sysConfigMapper.selectSysConfigList(new SysConfig());
        for (SysConfig config : configsList)
        {
            redisUtil.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
    }

    /**
     * 清空参数缓存数据
     */
    @Override
    public void clearConfigCache()
    {
        Collection<String> keys = redisUtil.keys(Constant.SYS_CONFIG_KEY + "*");
        redisUtil.del(keys);
    }

    /**
     * 重置参数缓存数据
     */
    @Override
    public void resetConfigCache()
    {
        clearConfigCache();
        loadingConfigCache();
    }

    /**
    * 根据Id查询单条数据
    */
    public SysConfig selectSysConfigById(Long configId){
        return sysConfigMapper.selectSysConfigById(configId);
    }

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数key
     * @return 参数键值
     */
    @Override
    public String selectSysConfigByKey(String configKey)
    {
        String configValue = Convert.toStr(redisUtil.getCacheObject(getCacheKey(configKey)));
        if (StringUtils.isNotEmpty(configValue))
        {
            return configValue;
        }
        SysConfig config = new SysConfig();
        config.setConfigKey(configKey);
        SysConfig retConfig = sysConfigMapper.selectConfig(config);
        if (StringUtils.isNotNull(retConfig))
        {
            redisUtil.setCacheObject(getCacheKey(configKey), retConfig.getConfigValue());
            return retConfig.getConfigValue();
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取验证码开关
     *
     * @return true开启，false关闭
     */
    public boolean selectCaptchaOnOff()
    {
        String captchaOnOff = selectSysConfigByKey("sys.account.captchaOnOff");
        if (StringUtils.isEmpty(captchaOnOff))
        {
            return true;
        }
        return Convert.toBool(captchaOnOff);
    }

    /**
     * 校验参数键名是否唯一
     *
     * @param config 参数配置信息
     * @return 结果
     */
    public String checkConfigKeyUnique(SysConfig config)
    {
        Long configId = StringUtils.isNull(config.getConfigId()) ? -1L : config.getConfigId();
        SysConfig sysConfig = new SysConfig();
        sysConfig.setConfigKey(config.getConfigKey());
        SysConfig info = sysConfigMapper.selectConfig(sysConfig);
        if (StringUtils.isNotNull(info) && info.getConfigId().longValue() != configId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String configKey)
    {
        return Constant.SYS_CONFIG_KEY + configKey;
    }
}
