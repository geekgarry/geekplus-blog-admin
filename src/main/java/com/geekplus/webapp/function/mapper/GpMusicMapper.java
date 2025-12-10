package com.geekplus.webapp.function.mapper;

import com.geekplus.webapp.function.entity.GpMusic;
import java.util.List;

/**
 * 在线音乐 在线音乐
 * Created by CodeGenerator on 2023/09/29.
 */
public interface GpMusicMapper {

    /**
    * 增加
    * @param gpMusic
    * @return 在线音乐
    */
    Integer insertGpMusic(GpMusic gpMusic);

    /**
    * 批量增加
    * @param gpMusicList
    * @return
    */
    public int batchInsertGpMusicList(List<GpMusic> gpMusicList);

    /**
    * 删除
    * @param id
    */
    Integer deleteGpMusicById(Integer id);

    /**
    * 批量删除
    */
    Integer deleteGpMusicByIds(Integer[] ids);

    /**
    * 修改
    * @param gpMusic
    */
    Integer updateGpMusic(GpMusic gpMusic);

    /**
    * 批量修改魔偶几个字段
    * @param ids
    */
    Integer batchUpdateGpMusicList(Integer[] ids);

    /**
    * 查询全部
    */
    List<GpMusic> selectGpMusicList(GpMusic gpMusic);

    /**
    * 查询全部,联合查询使用
    */
    List<GpMusic> selectUnionGpMusicList(GpMusic gpMusic);

    /**
    * 根据Id查询单条数据
    */
    GpMusic selectGpMusicById(Integer id);
}
