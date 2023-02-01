package com.chuqiyun.btvhost.controller.admin.node;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chuqiyun.btvhost.annotation.AdminLoginCheck;
import com.chuqiyun.btvhost.dao.RegionclassDao;
import com.chuqiyun.btvhost.dao.ServernodeDao;
import com.chuqiyun.btvhost.entity.Regionclass;
import com.chuqiyun.btvhost.entity.Servernode;
import com.chuqiyun.btvhost.model.GetNode;
import com.chuqiyun.btvhost.service.RegionclassService;
import com.chuqiyun.btvhost.utils.ObjectUtil;
import com.chuqiyun.btvhost.utils.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;

import static com.chuqiyun.btvhost.utils.EncryptUtil.base64Encode;

/**
 * @author mryunqi
 * @date 2023/1/13
 */
@Controller
@RequiredArgsConstructor
public class AdminNode {
    private final ServernodeDao servernodeDao;
    private final RegionclassService regionclassService;
    private final RegionclassDao regionclassDao;
    @AdminLoginCheck
    @RequestMapping(value = "/admin/node")
    public String node(Model model) {
        model.addAttribute("title", "服务器节点列表");
        model.addAttribute("page","region");
        model.addAttribute("page_tab","node");
        return "/admin/node";
    }
    @AdminLoginCheck
    @RequestMapping(value = "/admin/addNode")
    public String addNode(Model model) {
        model.addAttribute("title", "添加宝塔服务器节点");
        model.addAttribute("page","region");
        model.addAttribute("page_tab","node");
        return "/admin/addNode";
    }

    @AdminLoginCheck
    @ResponseBody
    @PostMapping(value = "/admin/addNodeDo")
    public ResponseResult<String> addNodeDoze(@RequestBody JSONObject data){
        Servernode node = new Servernode();
        node.setHost(data.getString("host"));
        node.setPort(data.getInteger("port"));
        node.setSkey(data.getString("skey"));
        node.setOs(data.getInteger("os"));
        node.setSoft(data.getInteger("soft"));
        node.setServerssl(data.getInteger("ssl"));
        node.setSatus(data.getInteger("status"));
        node.setCreatedate(new Date());
        String probeToken = data.getString("host")+System.currentTimeMillis()+"chuqiyun";
        probeToken = base64Encode(probeToken);
        node.setProbetoken(probeToken);
        String str = data.getString("group");
        String country = "";
        String city = "";
        if(!str.contains("-")) {
            country = str.trim();
        } else {
            String[] strs = str.split("-");
            country = strs[0].trim();
            city = strs[1].trim();
        }
        if ("".equals(country)){
            servernodeDao.insert(node);
            return ResponseResult.ok("添加成功");
        }else {
            QueryWrapper<Regionclass> regionclassQueryWrapper = new QueryWrapper<>();
            regionclassQueryWrapper.eq("firstClass",country);
            Regionclass regionClass = regionclassService.getOne(regionclassQueryWrapper);
            if (ObjectUtil.isNull(regionClass)){
                regionClass.setFirstclass(country);
                regionclassDao.insert(regionClass);
            }
            if ("".equals(city)){
                regionClass = regionclassService.getOne(regionclassQueryWrapper);
                node.setGroupclass(regionClass.getId());
                servernodeDao.insert(node);
                return ResponseResult.ok("添加成功");
            }
            QueryWrapper<Regionclass> parentClassQueryWrap = new QueryWrapper<>();
            parentClassQueryWrap.eq("firstClass",city);
            Regionclass parentClass = regionclassService.getOne(parentClassQueryWrap);
            if(ObjectUtil.isNull(parentClass)){
                parentClass.setFirstclass(city);
                parentClass.setParentclass(country);
                regionclassDao.insert(parentClass);
            }else {
                parentClass =regionclassService.getOne(parentClassQueryWrap);
            }
            node.setGroupclass(parentClass.getId());
            servernodeDao.insert(node);
            return ResponseResult.ok("添加成功");
        }

    }


    @AdminLoginCheck
    @ResponseBody
    @PostMapping(value = "/admin/upNodeDo")
    public ResponseResult<String> upNodeDoze(@RequestBody JSONObject data){
        Servernode node = new Servernode();
        node.setId(data.getInteger("id"));
        node.setHost(data.getString("host"));
        node.setPort(data.getInteger("port"));
        node.setSkey(data.getString("skey"));
        node.setOs(data.getInteger("os"));
        node.setSoft(data.getInteger("soft"));
        node.setServerssl(data.getInteger("ssl"));
        node.setSatus(data.getInteger("status"));
        node.setCreatedate(new Date());
        String probeToken = data.getString("host")+System.currentTimeMillis()+"chuqiyun";
        probeToken = base64Encode(probeToken);
        node.setProbetoken(probeToken);
        String str = data.getString("group");
        String country = "";
        String city = "";
        if(!str.contains("-")) {
            country = str.trim();
        } else {
            String[] strs = str.split("-");
            country = strs[0].trim();
            city = strs[1].trim();
        }
        if ("".equals(country)){
            servernodeDao.updateById(node);
            return ResponseResult.ok("添加成功");
        }else {
            QueryWrapper<Regionclass> regionclassQueryWrapper = new QueryWrapper<>();
            regionclassQueryWrapper.eq("firstClass",country);
            Regionclass regionClass = regionclassService.getOne(regionclassQueryWrapper);
            if (ObjectUtil.isNull(regionClass)){
                regionClass.setFirstclass(country);
                regionclassDao.updateById(regionClass);
            }
            if ("".equals(city)){
                regionClass = regionclassService.getOne(regionclassQueryWrapper);
                node.setGroupclass(regionClass.getId());
                servernodeDao.updateById(node);
                return ResponseResult.ok("添加成功");
            }
            QueryWrapper<Regionclass> parentClassQueryWrap = new QueryWrapper<>();
            parentClassQueryWrap.eq("firstClass",city);
            Regionclass parentClass = regionclassService.getOne(parentClassQueryWrap);
            if(ObjectUtil.isNull(parentClass)){
                parentClass.setFirstclass(city);
                parentClass.setParentclass(country);
                regionclassDao.updateById(parentClass);
            }else {
                parentClass =regionclassService.getOne(parentClassQueryWrap);
            }
            node.setGroupclass(parentClass.getId());
            servernodeDao.updateById(node);
            return ResponseResult.ok("添加成功");
        }

    }
}
