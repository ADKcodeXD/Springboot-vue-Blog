package com.adk.service;

import com.adk.vo.CategoryVo;
import com.adk.vo.Result;

public interface CategoryService {
    CategoryVo findCategoryById(Long categoryId);

    Result findAll();

    Result findAllDetails();

    Result findAllDetailsById(Long id);
}
