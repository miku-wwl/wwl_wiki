package com.weilai.wiki.service;

import com.weilai.wiki.domain.Ebook;
import com.weilai.wiki.domain.EbookExample;
import com.weilai.wiki.mapper.EbookMapper;
import com.weilai.wiki.req.EbookReq;
import com.weilai.wiki.resp.EbookResp;
import com.weilai.wiki.util.CopyUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class EbookService {
    @Resource
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

//        List<EbookResp> respList = new ArrayList<>();
//        for (Ebook ebook : ebookList) {
//            EbookResp ebookResp = new EbookResp();
//            BeanUtils.copyProperties(ebook, ebookResp);
//            respList.add(ebookResp);
//        }
        // 列表复制
        List<EbookResp> respList1 = CopyUtil.copyList(ebookList, EbookResp.class);

        return respList1;
    }
}
