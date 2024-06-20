package com.weilai.wiki.controller;

import com.weilai.wiki.req.EbookQueryReq;
import com.weilai.wiki.req.EbookSaveReq;
import com.weilai.wiki.resp.CommonResp;
import com.weilai.wiki.resp.EbookQueryResp;
import com.weilai.wiki.resp.PageResp;
import com.weilai.wiki.service.EbookService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ebook")
public class EbookController {
    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(EbookQueryReq req) {
        CommonResp<PageResp<EbookQueryResp>> resp = new CommonResp<>();
        PageResp<EbookQueryResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }

    /**
     *
     * @param req 用json方式的提交，需要用@RequestBody才能接收到，如果是用form表单方式的提交，就不需要加任何注解
     * @return
     */
    @PostMapping("/save")
    public CommonResp save(@RequestBody EbookSaveReq req) {
        CommonResp resp = new CommonResp<>();
        ebookService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id)
    {
        CommonResp<Object> resp = new CommonResp<>();
        ebookService.delete(id);
        return resp;
    }
}
