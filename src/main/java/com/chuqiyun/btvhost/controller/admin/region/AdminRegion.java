package com.chuqiyun.btvhost.controller.admin.region;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chuqiyun.btvhost.annotation.AdminLoginCheck;
import com.chuqiyun.btvhost.entity.RegionClassList;
import com.chuqiyun.btvhost.entity.Regionclass;
import com.chuqiyun.btvhost.entity.Regions;
import com.chuqiyun.btvhost.service.RegionclassService;
import com.chuqiyun.btvhost.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mryunqi
 * @date 2023/1/11
 */
@Controller
@RequiredArgsConstructor
public class AdminRegion {
    private final RegionclassService regionclassService;
    @AdminLoginCheck
    @RequestMapping(value = "/admin/region")
    public String adminRegion(Model model) {
        model.addAttribute("title", "地区管理");
        model.addAttribute("page","region");
        model.addAttribute("page_tab","region");
        model.addAttribute("region",regionList());
        return "admin/region";
    }

    private List<RegionClassList> regionList(){
        QueryWrapper<Regionclass> firsttregionclassQueryWrapper = new QueryWrapper<>();
        firsttregionclassQueryWrapper.eq("parentClass","0");
        List<Regionclass> firstRegions = regionclassService.list(firsttregionclassQueryWrapper);
        if (firstRegions.size() == 0){
            return null;
        }
        List<RegionClassList> list = new ArrayList<>();
        for (Regionclass parentClass:firstRegions){
            RegionClassList result = new RegionClassList();
            result.setParentClass(parentClass.getFirstclass());
            QueryWrapper<Regionclass> lowClass = new QueryWrapper<>();
            lowClass.eq("parentClass",parentClass.getFirstclass());
            List<Regionclass> lowRegions = regionclassService.list(lowClass);
            List<Regions> regions = new ArrayList<>();
            if (lowRegions.size() == 0){
                result.setRegions(regions);
            }else {
                for (Regionclass lowRegion:lowRegions){
                    Regions region = new Regions();
                    region.setId(lowRegion.getId());
                    region.setRegion(lowRegion.getFirstclass());
                    if (lowRegion.getNodelist() == null){
                        region.setNodenum(0);
                    }else {
                        region.setNodenum(JSONObject.parseObject(lowRegion.getNodelist()).size());
                    }
                    regions.add(region);
                }
                result.setRegions(regions);
            }
            String jsonStr = JsonUtil.readJsonFile("src/main/resources/json/country.json");
            JSONArray country = JSONArray.parseArray(jsonStr);
            assert country != null;
            for (int i = 0; i < country.size(); i++) {
                JSONObject countryJson = country.getJSONObject(i);
                String nameMatcher = "^"+parentClass.getFirstclass()+"(.*)$";
                if (countryJson.getString("name").matches(nameMatcher)){
                    result.setCode(countryJson.getString("code"));
                }
            }
            list.add(result);
        }
        return list;
    }

}
