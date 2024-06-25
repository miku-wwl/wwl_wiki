package com.weilai.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weilai.wiki.domain.DocExample;
import com.weilai.wiki.domain.Ebook;
import com.weilai.wiki.domain.EbookExample;
import com.weilai.wiki.mapper.DocMapper;
import com.weilai.wiki.mapper.EbookMapper;
import com.weilai.wiki.req.EbookQueryReq;
import com.weilai.wiki.req.EbookSaveReq;
import com.weilai.wiki.resp.EbookQueryResp;
import com.weilai.wiki.resp.PageResp;
import com.weilai.wiki.util.CopyUtil;
import com.weilai.wiki.util.SnowFlake;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class EbookService {

    private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);

    @Resource
    private EbookMapper ebookMapper;

    @Resource
    private DocMapper docMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<EbookQueryResp> list(EbookQueryReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        if (!ObjectUtils.isEmpty(req.getCategoryId1())) {
            if (req.getCategoryId1() != 0)
                criteria.andCategory1IdEqualTo(req.getCategoryId1());
            if (req.getCategoryId2() != 0)
                criteria.andCategory2IdEqualTo(req.getCategoryId2());
        }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);
        PageInfo<Ebook> pageInfo = new PageInfo<>(ebookList);
        // 写日志最好使用占位符{}，而不是用+拼接字符串
        LOG.info("总行数: {}", pageInfo.getTotal());

//        List<EbookResp> respList = new ArrayList<>();
//        for (Ebook ebook : ebookList) {
//            EbookResp ebookResp = new EbookResp();
//            BeanUtils.copyProperties(ebook, ebookResp);
//            respList.add(ebookResp);
//        }
        // 列表复制
        List<EbookQueryResp> respList = CopyUtil.copyList(ebookList, EbookQueryResp.class);
        PageResp<EbookQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);

        return pageResp;
    }

    /**
     * 保存
     */
    public void save(EbookSaveReq req) {
        Ebook ebook = CopyUtil.copy(req, Ebook.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            // 新增
            ebook.setId(snowFlake.nextId());
            ebookMapper.insertSelective(ebook);
        } else {
            // 更新
            ebookMapper.updateByPrimaryKey(ebook);
        }
    }


    /**
     * 删除
     */
    public void delete(Long id) {
        // 删除电子书前先删除其关联的所有文档
        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();
        criteria.andEbookIdEqualTo(id);
        docMapper.deleteByExample(docExample);
        ebookMapper.deleteByPrimaryKey(id);
    }
}
