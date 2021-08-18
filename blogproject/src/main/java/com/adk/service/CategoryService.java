package com.adk.service;

import com.adk.vo.CategoryVo;

public interface CategoryService {
    CategoryVo findCategoryById(Long categoryId);
}
