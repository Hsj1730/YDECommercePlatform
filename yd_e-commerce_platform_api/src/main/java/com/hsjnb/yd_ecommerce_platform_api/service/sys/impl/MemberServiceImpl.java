package com.hsjnb.yd_ecommerce_platform_api.service.sys.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hsjnb.yd_ecommerce_platform_api.common.Constant;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.MaterialDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.MemberBalanceDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.MemberDto;
import com.hsjnb.yd_ecommerce_platform_api.mapper.sys.MaterialMapper;
import com.hsjnb.yd_ecommerce_platform_api.mapper.sys.MemberMapper;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.MemberService;
import com.hsjnb.yd_ecommerce_platform_api.utils.MathUtil;
import com.hsjnb.yd_ecommerce_platform_api.utils.QiNiuYunUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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
 * @date : Created in 2022/04/01 10:48
 * @description :
 */

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    private final QiNiuYunUtil qiNiuYunUtil;

    private final MaterialMapper materialMapper;

    @Autowired
    public MemberServiceImpl(MemberMapper memberMapper,QiNiuYunUtil qiNiuYunUtil,MaterialMapper materialMapper) {
        this.memberMapper = memberMapper;
        this.qiNiuYunUtil = qiNiuYunUtil;
        this.materialMapper = materialMapper;
    }

    @Override
    public PageInfo<MemberDto> getMemberList(Map<String, Object> param) {
        PageHelper.startPage(param);
        Map<String,Object> map = JSONObject.parseObject(JSON.toJSONString(param.get("query")));
        List<MemberDto> memberList = memberMapper.getMemberList((String) map.get("value"), (String) map.get("type"));
        for (MemberDto memberDto : memberList) {
            memberDto.setUserImage(qiNiuYunUtil.getDownloadUrl(memberDto.getUserImage()));
        }
        return new PageInfo<>(memberList);
    }

    @Override
    public MemberBalanceDto getMemberBalance(Integer id) {
        return memberMapper.getMemberBalance(id);
    }

    @Override
    public Result modifyMemberBalance(Integer id, Map<String, Object> param) {
        String type = (String) param.get("type");
        BigDecimal modifyMoney = MathUtil.getBigDecimal(param.get("modifyMoney"));
        return memberBalanceProcess(id,type,modifyMoney);
    }

    @Override
    public void setMemberStatus(Integer userId, String enable) {
        memberMapper.setMemberStatus(userId,enable);
    }

    @Override
    public MemberDto getMemberInfo(Integer userId) {
        MemberDto memberInfo = memberMapper.getMemberInfo(userId);
        MaterialDto userImageByKey;
        userImageByKey = materialMapper.getMaterialByKey(memberInfo.getUserImage());
        if (userImageByKey == null) {
            userImageByKey = new MaterialDto(0,null,"默认头像.jpg",memberInfo.getUserImage(),"","1","1");
        }
        userImageByKey.setRealUrl(qiNiuYunUtil.getDownloadUrl(userImageByKey.getUrl()));
        MaterialDto[] userImageArr = {userImageByKey};
        memberInfo.setUserImageArr(userImageArr);
        return memberInfo;
    }

    @Override
    public Result saveMemberInfo(MemberDto dto) {
        memberMapper.saveMemberInfo(dto);
        return Result.success(null);
    }

    @Override
    public Result deleteMember(Integer userId) {
        memberMapper.deleteMember(userId);
        return Result.success(null);
    }

    @Override
    public Result checkMobile(Integer userId, String phone) {
        return Result.success(memberMapper.checkMobile(userId, phone));
    }

    public synchronized Result memberBalanceProcess(Integer id,String type,BigDecimal modifyMoney) {
        // 查询用户余额
        BigDecimal memberBalance = memberMapper.queryMemberBalance(id);
        BigDecimal newMemberBalance;
        if (type.equals(Constant.OPERATION_TYPE_1)) {
            newMemberBalance = memberBalance.add(modifyMoney);
            if (newMemberBalance.compareTo(new BigDecimal("99999999.99")) > 0) {
                return Result.fail("增加后余额超限");
            }
        } else if (type.equals(Constant.OPERATION_TYPE_2)) {
            newMemberBalance = memberBalance.subtract(modifyMoney);
            if (newMemberBalance.compareTo(new BigDecimal("0.00")) < 0) {
                return Result.fail("余额不足");
            }
        } else {
            newMemberBalance = new BigDecimal("0.00");
        }
        memberMapper.modifyMemberBalance(id,newMemberBalance);
        return Result.success(null);
    }


}
