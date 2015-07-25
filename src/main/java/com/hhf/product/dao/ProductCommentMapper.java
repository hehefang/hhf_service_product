package com.hhf.product.dao;

import com.hhf.model.product.ProductComment;

public interface ProductCommentMapper {
    int deleteByPrimaryKey(Integer pCommentId);

    int insert(ProductComment record);

    int insertSelective(ProductComment record);

    ProductComment selectByPrimaryKey(Integer pCommentId);

    int updateByPrimaryKeySelective(ProductComment record);

    int updateByPrimaryKey(ProductComment record);
}