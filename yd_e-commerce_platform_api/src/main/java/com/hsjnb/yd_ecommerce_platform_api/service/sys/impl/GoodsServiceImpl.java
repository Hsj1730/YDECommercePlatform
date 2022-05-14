package com.hsjnb.yd_ecommerce_platform_api.service.sys.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.*;
import com.hsjnb.yd_ecommerce_platform_api.entity.Goods;
import com.hsjnb.yd_ecommerce_platform_api.entity.GoodsAttr;
import com.hsjnb.yd_ecommerce_platform_api.entity.GoodsAttrResult;
import com.hsjnb.yd_ecommerce_platform_api.entity.GoodsAttrValue;
import com.hsjnb.yd_ecommerce_platform_api.mapper.sys.GoodsCategoryMapper;
import com.hsjnb.yd_ecommerce_platform_api.mapper.sys.GoodsMapper;
import com.hsjnb.yd_ecommerce_platform_api.mapper.sys.MaterialMapper;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.GoodsService;
import com.hsjnb.yd_ecommerce_platform_api.utils.BarCodeGenerator;
import com.hsjnb.yd_ecommerce_platform_api.utils.QiNiuYunUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

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
 * @date : Created in 2022/03/30 21:32
 * @description :
 */

@Slf4j
@Service
public class GoodsServiceImpl implements GoodsService {

    private final GoodsMapper goodsMapper;

    private final GoodsCategoryMapper goodsCategoryMapper;

    private final QiNiuYunUtil qiNiuYunUtil;

    private final MaterialMapper materialMapper;

    @Autowired
    public GoodsServiceImpl(GoodsMapper goodsMapper,GoodsCategoryMapper goodsCategoryMapper,
                            QiNiuYunUtil qiNiuYunUtil,MaterialMapper materialMapper) {
        this.goodsMapper = goodsMapper;
        this.goodsCategoryMapper = goodsCategoryMapper;
        this.qiNiuYunUtil = qiNiuYunUtil;
        this.materialMapper = materialMapper;
    }

    @Override
    public PageInfo<Goods> getGoodsList(Map<String, Object> param) {
        PageHelper.startPage(param);
        Map<String,Object> map = JSONObject.parseObject(JSON.toJSONString(param.get("query")));
        List<Goods> goodsList = goodsMapper.getGoodsList((String) map.get("value"), (String) map.get("type"), (String) param.get("isShow"));
        for (Goods goods : goodsList) {
            goods.setImage(qiNiuYunUtil.getDownloadUrl(goods.getImage()));
        }
        return new PageInfo<>(goodsList);
    }

    @Override
    public void setGoodsIsShow(Integer id, String status) {
        goodsMapper.setGoodsIsShow(id,status);
    }

    @Override
    public GoodsDto getGoodsInfo(Integer id) {
        GoodsDto goods = goodsMapper.getGoodsInfo(id);
        // 获取商品得图片资源信息
        MaterialDto imageByKey = materialMapper.getMaterialByKey(goods.getImage());
        imageByKey.setRealUrl(qiNiuYunUtil.getDownloadUrl(imageByKey.getUrl()));
        MaterialDto[] imageArr = {imageByKey};
        String sliderImage = goods.getSliderImage();
        String[] split = sliderImage.split(",");
        MaterialDto[] sliderImageArr = new MaterialDto[split.length];
        for (int i = 0; i < split.length; i++) {
            MaterialDto sliderImageByKey = materialMapper.getMaterialByKey(split[i]);
            sliderImageByKey.setRealUrl(qiNiuYunUtil.getDownloadUrl(sliderImageByKey.getUrl()));
            sliderImageArr[i] = sliderImageByKey;
        }
        goods.setImageArr(imageArr);
        goods.setSliderImageArr(sliderImageArr);
        return goods;
    }

    @Override
    public Result saveGoodsInfo(GoodsDto dto) {
        // 判断分类是否为二级分类
        if (goodsCategoryMapper.checkCategory(dto.getCateId()) == 0) {
            return Result.fail("商品分类只能为二级分类");
        }
        if (dto.getId() != null) { // 修改
            goodsMapper.editGoods(dto);
        } else { // 添加
            FutureTask<String> task;
            Callable<String> callable = () -> BarCodeGenerator.getD(StringUtils.replace(UUID.randomUUID().toString(), "-", ""));
            task = new FutureTask<>(callable);
            new Thread(task).start();
            try {
                dto.setBarCode(task.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                log.error("商品码生成失败");
                return Result.fail("商品码生成失败");
            }
            goodsMapper.addGoods(dto);
        }
        return Result.success(null);
    }

    @Override
    public Result deleteGoods(Integer id) {
        goodsMapper.deleteGoods(id);
        return Result.success(null);
    }

    @Override
    public String getGoodsAttrResult(Integer id) {
        return goodsMapper.getGoodsAttrResult(id);
    }

    @Override
    public Result isFormatAttr(Integer id, String jsonStr) {
        if (ObjectUtil.isNull(id)) {
            return Result.fail("产品不存在");
        }
        GoodsDto goodsInfo = goodsMapper.getGoodsInfo(id);
        String link = qiNiuYunUtil.getDownloadUrl(goodsInfo.getImage());
        DetailDto detailDto = attrFormat(jsonStr);
        List<GoodsFormatDto> newList = new ArrayList<>();
        for (Map<String, Map<String, String>> map : detailDto.getRes()) {
            GoodsFormatDto goodsFormatDto = new GoodsFormatDto();
            goodsFormatDto.setDetail(map.get("detail"));
            goodsFormatDto.setCost(goodsInfo.getCost());
            goodsFormatDto.setPrice(goodsInfo.getPrice());
            goodsFormatDto.setLink(link);
            goodsFormatDto.setSales(goodsInfo.getSales());
            goodsFormatDto.setPic(goodsInfo.getImage());
            goodsFormatDto.setCheck(false);
            newList.add(goodsFormatDto);
        }
        return Result.success(newList);
    }

    @Override
    public Result uploadAttrPic(MultipartFile multipartFile) {
        String pic = qiNiuYunUtil.upload(multipartFile);
        String link = qiNiuYunUtil.getDownloadUrl(pic);
        Map<String,String> result = new HashMap<>();
        result.put("pic",pic);
        result.put("link",link);
        return Result.success(result);
    }

    @Override
    public Result setAttr(Integer id, String jsonStr) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        List<FormatDetailDto> attrList = JSON.parseArray(jsonObject.get("items").toString(), FormatDetailDto.class);
        List<GoodsFormatDto> valueList = JSON.parseArray(jsonObject.get("attrs").toString(), GoodsFormatDto.class);

        List<GoodsAttr> attrGroup = new ArrayList<>();
        for (FormatDetailDto formatDetailDto : attrList) {
            GoodsAttr goodsAttr = new GoodsAttr();
            goodsAttr.setGoodsId(id);
            goodsAttr.setAttrName(formatDetailDto.getValue());
            goodsAttr.setAttrValues(StrUtil.join(",",formatDetailDto.getDetail()));
            attrGroup.add(goodsAttr);
        }

        List<GoodsAttrValue> valueGroup = new ArrayList<>();
        for (GoodsFormatDto goodsFormatDto : valueList) {
            GoodsAttrValue goodsAttrValue = new GoodsAttrValue();
            goodsAttrValue.setGoodsId(id);
            List<String> stringList = new ArrayList<>(goodsFormatDto.getDetail().values());
            Collections.sort(stringList);
            goodsAttrValue.setSku(StrUtil.join(",",stringList));
            goodsAttrValue.setPrice(goodsFormatDto.getPrice());
            goodsAttrValue.setCost(goodsFormatDto.getCost());
            goodsAttrValue.setStock(goodsFormatDto.getSales());
            goodsAttrValue.setUnique(UUID.randomUUID().toString().replaceAll("-", ""));
            goodsAttrValue.setImage(goodsFormatDto.getPic());
            valueGroup.add(goodsAttrValue);
        }

        if (attrGroup.isEmpty() || valueGroup.isEmpty()) {
            Result.fail("请设置至少一个属性!");
        }

        // 如果设置sku 处理价格与库存
        BigDecimal minPrice = valueGroup
                .stream()
                .map(GoodsAttrValue::getPrice)
                .min(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);

        // 计算库存
        Integer stock = valueGroup
                .stream()
                .map(GoodsAttrValue::getStock)
                .reduce(Integer::sum)
                .orElse(0);

        Goods goods = Goods
                .builder()
                .stock(stock)
                .price(minPrice)
                .id(id)
                .build();
        goodsMapper.updateGoodsByAttr(goods);

        // 设计属性
        DecimalFormat decimalFormat = new DecimalFormat("000");
        int num = 0;
        for (GoodsAttrValue goodsAttrValue : valueGroup) {
            goodsAttrValue.setBarCode(decimalFormat.format(++num));
        }

        // 插入之前清空
        clearGoodsAttr(id,false);

        // 保存属性
        goodsMapper.saveGoodsAttr(attrGroup);

        // 保存值
        goodsMapper.saveGoodsAttrValue(valueGroup);

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("attr", jsonObject.get("items"));
        map.put("value", jsonObject.get("attrs"));

        // 保存结果
        setResult(map,id);
        return Result.success(null);
    }

    @Override
    public Result clearGoodsAttr(Integer id, boolean isActive) {
        if (ObjectUtil.isNull(id)) {
            return Result.fail("商品不存在");
        }
        goodsMapper.deleteGoodsAttr(id);
        goodsMapper.deleteGoodsAttrValue(id);
        if (isActive) {
            goodsMapper.deleteGoodsAttrResult(id);
        }
        return Result.success(null);
    }

    /**
     * 组合规则属性算法
     * @param jsonStr jsonStr
     * @return DetailDto
     */
    public DetailDto attrFormat(String jsonStr) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        List<FormatDetailDto> formatDetailDtoList = JSON.parseArray(jsonObject.getString("items"), FormatDetailDto.class);
        List<String> data = new ArrayList<>();
        List<Map<String, Map<String, String>>> res = new ArrayList<>();
        if (formatDetailDtoList.size() > 1) {
            for (int i = 0; i < formatDetailDtoList.size() - 1; i++) {
                if (i == 0) {
                    data = formatDetailDtoList.get(i).getDetail();
                }
                List<String> tmp = new LinkedList<>();
                for (String v : data) {
                    for (String g : formatDetailDtoList.get(i + 1).getDetail()) {
                        String rep2;
                        if (i == 0) {
                            rep2 = formatDetailDtoList.get(i).getValue() + "_" + v + "-"
                                    + formatDetailDtoList.get(i + 1).getValue() + "_" + g;
                        } else {
                            rep2 = v + "-"
                                    + formatDetailDtoList.get(i + 1).getValue() + "_" + g;
                        }
                        tmp.add(rep2);
                        if (i == formatDetailDtoList.size() - 2) {
                            Map<String, Map<String, String>> rep4 = new LinkedHashMap<>();
                            Map<String, String> repTemp = new LinkedHashMap<>();
                            for (String h : rep2.split("-")) {
                                List<String> rep3 = Arrays.asList(h.split("_"));
                                if (rep3.size() > 1) {
                                    repTemp.put(rep3.get(0), rep3.get(1));
                                } else {
                                    repTemp.put(rep3.get(0), "");
                                }
                            }
                            rep4.put("detail", repTemp);
                            res.add(rep4);
                        }
                    }
                }
                if (!tmp.isEmpty()) {
                    data = tmp;
                }
            }
        } else {
            List<String> dataArr = new ArrayList<>();
            for (FormatDetailDto formatDetailDto : formatDetailDtoList) {
                for (String str : formatDetailDto.getDetail()) {
                    Map<String, Map<String, String>> map2 = new LinkedHashMap<>();
                    dataArr.add(formatDetailDto.getValue() + "_" + str);
                    Map<String, String> map1 = new LinkedHashMap<>();
                    map1.put(formatDetailDto.getValue(), str);
                    map2.put("detail", map1);
                    res.add(map2);
                }
            }
            String s = StrUtil.join("-", dataArr);
            data.add(s);
        }
        DetailDto detailDTO = new DetailDto();
        detailDTO.setData(data);
        detailDTO.setRes(res);
        return detailDTO;
    }

    public void setResult(Map<String, Object> map,Integer id) {
        GoodsAttrResult goodsAttrResult = new GoodsAttrResult();
        goodsAttrResult.setGoodsId(id);
        goodsAttrResult.setResult(JSON.toJSONString(map));
        goodsMapper.deleteGoodsAttrResult(id);
        goodsMapper.saveGoodsAttrResult(goodsAttrResult);
    }
}
