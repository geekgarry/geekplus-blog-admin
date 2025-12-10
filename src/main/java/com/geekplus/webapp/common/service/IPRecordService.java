package com.geekplus.webapp.common.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * author     : geekplus
 * email      :
 * date       : 6/15/25 12:16 AM
 * description: //TODO
 */
@Service
public class IPRecordService {

    private final List<String> ipList = new ArrayList<>();

    public void recordIP(String ip) {
        ipList.add(ip);
    }

    public List<String> getAllIPs() {
        return new ArrayList<>(ipList); // 返回一个副本，防止外部修改
    }
}
