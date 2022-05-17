package com.hsjnb.yd_ecommerce_platform_api.utils;

import com.hsjnb.yd_ecommerce_platform_api.mapper.ScheduleTaskMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * █████▒█    ██  ▄████▄   ██ ▄█▀       ██████╗ ██╗   ██╗ ██████╗
 * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒        ██╔══██╗██║   ██║██╔════╝
 * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░        ██████╔╝██║   ██║██║  ███╗
 * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄        ██╔══██╗██║   ██║██║   ██║
 * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄       ██████╔╝╚██████╔╝╚██████╔╝
 * ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒       ╚═════╝  ╚═════╝  ╚═════╝
 * ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
 * ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
 * ░     ░ ░      ░  ░
 *
 * @author : Hsj1730
 * @version : 1.0
 * @date : Created in 2022/03/30 0:33
 * @description : 定时任务
 */

@Slf4j
@Component
public class ScheduleTaskUtil {

    private final ScheduleTaskMapper scheduleTaskMapper;

    private final QiNiuYunUtil qiNiuYunUtil;

    @Autowired
    public ScheduleTaskUtil(ScheduleTaskMapper scheduleTaskMapper,QiNiuYunUtil qiNiuYunUtil) {
        this.scheduleTaskMapper = scheduleTaskMapper;
        this.qiNiuYunUtil = qiNiuYunUtil;
    }

    // 每周星期一凌晨删除七牛云上无效得图片
    @Scheduled(cron = "0 0 0 * * 1")
    public void deleteFailImg() {
        List<String> imgList = scheduleTaskMapper.queryFailImage();
        String[] imgArray = imgList.toArray(new String[0]);
        qiNiuYunUtil.deleteList(imgArray);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateGoodsOrderStatus() {
        // 更新顶到状态到确认收货
        scheduleTaskMapper.updateGoodsOrderStatus("2");
        // 更新顶动感状态到交易完成
        scheduleTaskMapper.updateGoodsOrderStatus("3");
    }

}
