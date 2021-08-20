package com.adk.service.Impl;

import com.adk.dao.mapper.CategoryMapper;
import com.adk.pojo.Category;
import com.adk.service.CategoryService;
import com.adk.vo.CategoryVo;
import com.adk.vo.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryVo findCategoryById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }

    @Override
    public Result findAll() {
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.select(Category::getId,Category::getCategoryName);
        List<Category> categories = categoryMapper.selectList(queryWrapper);
        //转换为vo
        return Result.success(copyList(categories));
    }

    @Override
    public Result findAllDetails() {
        List<Category> categories = categoryMapper.selectList(new LambdaQueryWrapper<>());
        //转换为vo
        return Result.success(copyList(categories));
    }

    private List<CategoryVo> copyList(List<Category> categories) {
        List<CategoryVo> categoryVoList=new ArrayList<>();
        for (Category category : categories) {
            categoryVoList.add(copy(category));
        }
        return categoryVoList;
    }

    private CategoryVo copy(Category category) {
        CategoryVo categoryVo=new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }
}
