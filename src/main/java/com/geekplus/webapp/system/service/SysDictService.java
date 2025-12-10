package com.geekplus.webapp.system.service;

import com.geekplus.webapp.system.entity.SysDict;
//import com.geekplus.core.Service;
import java.util.List;


/**
 * 数据字典 数据字典
 * Created by CodeGenerator on 2025/09/20.
 */
public interface SysDictService {

    /**
    * 查询全部
    */
    public List<SysDict> querySysDictList(SysDict sysDict);

    /**
    * 查询全部，用作联合查询使用(在基础上修改即可)
    */
    public List<SysDict> queryUnionSysDictList(SysDict sysDict);

    /**
    * 根据Id查询单条数据
    */
    public SysDict querySysDictById(Long id);

    /**
    * 增加
    * @param sysDict
    * @return 数据字典
    */
    public Integer addSysDict(SysDict sysDict);

    /**
    * 批量增加
    * @param sysDictList
    * @return 数据字典
    */
    public Integer batchAddSysDictList(List<SysDict> sysDictList);

    /**
    * 删除
    * @param id
    */
    public Integer removeSysDictById(Long id);

    /**
    * 逻辑删除,更新删除标志字段
    * @param id
    */
    Integer modifyDelFlagById(Long id);

    /**
    * 批量删除
    * @param ids
    */
    public Integer removeSysDictByIds(Long[] ids);

    /**
    * 逻辑批量删除,更新删除标志字段
    * @param ids
    */
    Integer modifyDelFlagByIds(Long[] ids);

    /**
    * 修改
    * @param sysDict
    */
    public Integer modifySysDict(SysDict sysDict);

    /**
    * 批量修改
    * @param ids
    */
    public Integer batchModifySysDictList(Long[] ids);

    /**
     * 加载字典缓存数据
     */
    public void loadingDictCache();

    /**
     * 清空字典缓存数据
     */
    public void clearDictCache();

    /**
     * 重置字典缓存数据
     */
    public void resetDictCache();

    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
    public List<SysDict> selectDictTypeAll();
}
