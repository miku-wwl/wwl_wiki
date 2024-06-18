package com.weilai.wiki.controller;

import com.weilai.wiki.domain.Ebook;
import com.weilai.wiki.req.EbookReq;
import com.weilai.wiki.resp.CommonResp;
import com.weilai.wiki.resp.EbookResp;
import com.weilai.wiki.service.EbookService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ebook")
public class EbookController {
    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp<List<EbookResp>> list(EbookReq req) {
        CommonResp<List<EbookResp>> resp = new CommonResp<>();
        List<EbookResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }
}
