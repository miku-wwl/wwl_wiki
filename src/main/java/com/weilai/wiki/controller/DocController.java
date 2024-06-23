package com.weilai.wiki.controller;

import com.weilai.wiki.req.DocQueryReq;
import com.weilai.wiki.req.DocSaveReq;
import com.weilai.wiki.resp.CommonResp;
import com.weilai.wiki.resp.DocQueryResp;
import com.weilai.wiki.resp.PageResp;
import com.weilai.wiki.service.DocService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/doc")
public class DocController {

    @Resource
    private DocService docService;

    @GetMapping("/all")
    public CommonResp all() {
        CommonResp<List<DocQueryResp>> resp = new CommonResp<>();
        List<DocQueryResp> list = docService.all();
        resp.setContent(list);
        return resp;
    }

    @GetMapping("/list")
    public CommonResp list(@Valid DocQueryReq req) {
        CommonResp<PageResp<DocQueryResp>> resp = new CommonResp<>();
        PageResp<DocQueryResp> list = docService.list(req);
        resp.setContent(list);
        return resp;
    }

    /**
     * @param req 用json方式的提交，需要用@RequestBody才能接收到，如果是用form表单方式的提交，就不需要加任何注解
     * @return
     */
    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody DocSaveReq req) {
        CommonResp resp = new CommonResp<>();
        docService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{idsStr}")
    public CommonResp delete(@PathVariable String idsStr) {
        CommonResp<Object> resp = new CommonResp<>();
        if (!ObjectUtils.isEmpty(idsStr)) {
            List<String> list = Arrays.asList(idsStr.split(","));
            docService.delete(list);
        }
        return resp;
    }

    @GetMapping("/find-content/{id}")
    public CommonResp findContent(@PathVariable Long id) {
        CommonResp<String> resp = new CommonResp<>();
        String content = docService.findContent(id);
        resp.setContent(content);
        return resp;
    }
}