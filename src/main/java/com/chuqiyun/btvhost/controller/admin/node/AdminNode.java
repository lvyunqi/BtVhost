package com.chuqiyun.btvhost.controller.admin.node;

import com.chuqiyun.btvhost.annotation.AdminLoginCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author mryunqi
 * @date 2023/1/13
 */
@Controller
@RequiredArgsConstructor
public class AdminNode {
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
}
