package com.weilai.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weilai.wiki.domain.Category;
import com.weilai.wiki.domain.CategoryExample;
import com.weilai.wiki.mapper.CategoryMapper;
import com.weilai.wiki.req.CategoryQueryReq;
import com.weilai.wiki.req.CategorySaveReq;
import com.weilai.wiki.resp.CategoryQueryResp;
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
public class CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<CategoryQueryResp> list(CategoryQueryReq req) {
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }

        // 支持分页，且只对遇到的第一个select起作用
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        // 写日志最好使用占位符{}，而不是用+拼接字符串
        LOG.info("总行数：{}", pageInfo.getTotal());

//        List<CategoryResp> respList = new ArrayList<>();
//        for (Category category : categoryList) {
////            CategoryResp categoryResp = new CategoryResp();
////            BeanUtils.copyProperties(category, categoryResp);
//
//            // 对象复制
//            CategoryResp categoryResp = CopyUtil.copy(category, CategoryResp.class);
//
//            respList.add(categoryResp);
//        }

        // 列表复制
        List<CategoryQueryResp> respList = CopyUtil.copyList(categoryList, CategoryQueryResp.class);

        PageResp<CategoryQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);

        return pageResp;
    }

    /**
     * 保存
     *
     * @param req
     */
    public void save(CategorySaveReq req) {
        Category category = CopyUtil.copy(req, Category.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            // 新增
            category.setId(snowFlake.nextId());
            categoryMapper.insertSelective(category);
        } else {
            // 更新
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    /**
     * 删除
     *
     * @param id
     */
    public void delete(Long id) {
        categoryMapper.deleteByPrimaryKey(id);
    }
}