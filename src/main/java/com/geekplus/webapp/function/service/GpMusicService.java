package com.geekplus.webapp.function.service;

import com.geekplus.webapp.function.entity.GpMusic;
//import com.geekplus.core.Service;
import java.util.List;


/**
 * 在线音乐 在线音乐
 * Created by CodeGenerator on 2023/09/29.
 */
public interface GpMusicService {

    /**
    * 增加
    * @param gpMusic
    * @return 在线音乐
    */
    public Integer insertGpMusic(GpMusic gpMusic);

    /**
    * 批量增加
    * @param gpMusicList
    * @return 在线音乐
    */
    public Integer batchInsertGpMusicList(List<GpMusic> gpMusicList);

    /**
    * 删除
    * @param id
    */
    public Integer deleteGpMusicById(Integer id);

    /**
    * 批量删除某几个字段
    */
    public Integer deleteGpMusicByIds(Integer[] ids);

    /**
    * 修改
    * @param gpMusic
    */
    public Integer updateGpMusic(GpMusic gpMusic);

    /**
    * 批量修改
    * @param ids
    */
    public Integer batchUpdateGpMusicList(Integer[] ids);

    /**
    * 查询全部
    */
    public List<GpMusic> selectGpMusicList(GpMusic gpMusic);

    /**
    * 查询全部，用作联合查询使用(在基础上修改即可)
    */
    public List<GpMusic> selectUnionGpMusicList(GpMusic gpMusic);

    /**
    * 根据Id查询单条数据
    */
    public GpMusic selectGpMusicById(Integer id);
}
