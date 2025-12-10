package com.geekplus.webapp.function.service.impl;

import com.geekplus.webapp.function.mapper.GpMusicMapper;
import com.geekplus.webapp.function.entity.GpMusic;
import com.geekplus.webapp.function.service.GpMusicService;
//import com.geekplus.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2023/09/29.
 */
@Service
@Transactional
public class GpMusicServiceImpl implements GpMusicService {
    @Resource
    private GpMusicMapper gpMusicMapper;

    /**
    * 增加
    * @param gpMusic
    * @return 在线音乐
    */
    public Integer insertGpMusic(GpMusic gpMusic){
        return gpMusicMapper.insertGpMusic(gpMusic);
    }

    /**
    * 批量增加
    * @param gpMusicList
    * @return 在线音乐
    */
    public Integer batchInsertGpMusicList(List<GpMusic> gpMusicList){
        return gpMusicMapper.batchInsertGpMusicList(gpMusicList);
    }

    /**
    * 删除
    * @param id
    */
    public Integer deleteGpMusicById(Integer id){
        return gpMusicMapper.deleteGpMusicById(id);
    }

    /**
    * 批量删除
    */
    public Integer deleteGpMusicByIds(Integer[] ids){
        return gpMusicMapper.deleteGpMusicByIds(ids);
    }

    /**
    * 修改
    * @param gpMusic
    */
    public Integer updateGpMusic(GpMusic gpMusic){
        return gpMusicMapper.updateGpMusic(gpMusic);
    }

    /**
    * 批量修改某几个字段
    * @param ids
    */
    public Integer batchUpdateGpMusicList(Integer[] ids){
        return gpMusicMapper.batchUpdateGpMusicList(ids);
    }

    /**
    * 查询全部
    */
    public List<GpMusic> selectGpMusicList(GpMusic gpMusic){
        return gpMusicMapper.selectGpMusicList(gpMusic);
    }

    /**
    * 查询全部,用于联合查询，在此基础做自己的定制改动
    */
    public List<GpMusic> selectUnionGpMusicList(GpMusic gpMusic){
        return gpMusicMapper.selectUnionGpMusicList(gpMusic);
    }

    /**
    * 根据Id查询单条数据
    */
    public GpMusic selectGpMusicById(Integer id){
        return gpMusicMapper.selectGpMusicById(id);
    }
}
