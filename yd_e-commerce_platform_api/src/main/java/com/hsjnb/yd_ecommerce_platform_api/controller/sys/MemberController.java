package com.hsjnb.yd_ecommerce_platform_api.controller.sys;

import com.hsjnb.yd_ecommerce_platform_api.common.Constant;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.MemberDto;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
 * @date : Created in 2022/04/01 10:46
 * @description :
 */

@Api(tags = "系统管理 - 会员接口")
@RestController
@RequestMapping(value = "member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 查询会员列表
     * @param param param
     * @return Result
     */
    @PostMapping(value = "getMemberList", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "查询会员列表", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result getMemberList(@RequestBody Map<String,Object> param) {
        return Result.success(memberService.getMemberList(param));
    }

    /**
     * 查询会员余额
     * @param id id
     * @return Result
     */
    @PostMapping(value = "getMemberBalance/{id}", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "查询会员余额", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result getMemberBalance(@PathVariable Integer id) {
        return Result.success(memberService.getMemberBalance(id));
    }

    /**
     * 修改会员余额
     * @param id id
     * @param param param
     * @return Result
     */
    @PostMapping(value = "modifyMemberBalance/{id}", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "修改会员余额", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result modifyMemberBalance(@PathVariable Integer id,@RequestBody Map<String,Object> param) {
        return memberService.modifyMemberBalance(id,param);
    }

    /**
     * 修改会员状态
     * @param userId userId
     * @param enable enable
     * @return Result
     */
    @PostMapping(value = "setMemberStatus/{id}",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "修改会员状态", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result setMemberStatus(@PathVariable("id") Integer userId,String enable) {
        memberService.setMemberStatus(userId,enable);
        return Result.success(null);
    }

    /**
     * 获取会员信息
     * @param userId userId
     * @return Result
     */
    @PostMapping(value = "getMemberInfo/{id}",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "修改会员状态", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result getMemberInfo(@PathVariable("id") Integer userId) {
        return Result.success(memberService.getMemberInfo(userId));
    }

    /**
     * 保存会员信息
     * @param dto dto
     * @return Result
     */
    @PostMapping(value = "saveMemberInfo",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "保存会员信息", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result saveMemberInfo(@RequestBody MemberDto dto) {
        return memberService.saveMemberInfo(dto);
    }

    /**
     * 删除会员
     * @param userId userId
     * @return Result
     */
    @PostMapping(value = "deleteMember/{id}",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "删除会员", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result deleteMember(@PathVariable("id") Integer userId) {
        return memberService.deleteMember(userId);
    }

    /**
     * 验证手机号是否存在
     * @param userId userId
     * @return Result
     */
    @PostMapping(value = "checkMobile/{id}/{mobile}",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "删除会员", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result checkMobile(@PathVariable("id") Integer userId,@PathVariable("mobile") String phone) {
        return memberService.checkMobile(userId,phone);
    }

}
