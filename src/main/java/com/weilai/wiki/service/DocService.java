package com.weilai.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weilai.wiki.domain.Doc;
import com.weilai.wiki.domain.DocExample;
import com.weilai.wiki.mapper.DocMapper;
import com.weilai.wiki.req.DocQueryReq;
import com.weilai.wiki.req.DocSaveReq;
import com.weilai.wiki.resp.DocQueryResp;
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
public class DocService {

    private static final Logger LOG = LoggerFactory.getLogger(DocService.class);

    @Resource
    private DocMapper docMapper;

    @Resource
    private SnowFlake snowFlake;

    public List<DocQueryResp> all() {
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        List<Doc> docList = docMapper.selectByExample(docExample);

        // 列表复制
        List<DocQueryResp> list = CopyUtil.copyList(docList, DocQueryResp.class);

        return list;
    }

    public PageResp<DocQueryResp> list(DocQueryReq req) {
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        DocExample.Criteria criteria = docExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }


        // 支持分页，且只对遇到的第一个select起作用
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Doc> docList = docMapper.selectByExample(docExample);

        PageInfo<Doc> pageInfo = new PageInfo<>(docList);
        // 写日志最好使用占位符{}，而不是用+拼接字符串
        LOG.info("总行数：{}", pageInfo.getTotal());

//        List<DocResp> respList = new ArrayList<>();
//        for (Doc doc : docList) {
////            DocResp docResp = new DocResp();
////            BeanUtils.copyProperties(doc, docResp);
//
//            // 对象复制
//            DocResp docResp = CopyUtil.copy(doc, DocResp.class);
//
//            respList.add(docResp);
//        }

        // 列表复制
        List<DocQueryResp> respList = CopyUtil.copyList(docList, DocQueryResp.class);

        PageResp<DocQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);

        return pageResp;
    }

    /**
     * 保存
     *
     * @param req
     */
    public void save(DocSaveReq req) {
        Doc doc = CopyUtil.copy(req, Doc.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            // 新增
            doc.setId(snowFlake.nextId());
            docMapper.insertSelective(doc);
        } else {
            // 更新
            docMapper.updateByPrimaryKey(doc);
        }
    }

    /**
     * 删除
     *
     * @param id
     */
    public void delete(Long id) {
        docMapper.deleteByPrimaryKey(id);
    }
}