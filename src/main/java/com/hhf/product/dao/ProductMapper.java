package com.hhf.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hhf.common.mybatis.Page;
import com.hhf.model.product.Product;
import com.hhf.param.product.ProductCondition;

public interface ProductMapper {
	int deleteByPrimaryKey(Integer prodId);

	int insert(Product record);

	int insertSelective(Product record);

	Product selectByPrimaryKey(Integer prodId);

	int updateByPrimaryKeySelective(Product record);

	int updateByPrimaryKeyWithBLOBs(Product record);

	int updateByPrimaryKey(Product record);

	boolean batchUpdateProdByCondition(@Param("idList") List<Integer> idList,
			@Param("product") Product product);

	Product getProductByProdCode(String prodCode);

	List<Product> searchOnlineProductPage(
			@Param("productCondition") ProductCondition productCondition,
			@Param("sortField") String sortField,
			@Param("sortDirection") String sortDirection,
			@Param("page") Page<Product> page);

	List<Product> searchProductByConditionPage(
			@Param("productCondition") ProductCondition productCondition,
			@Param("sortField") String sortField,
			@Param("sortDirection") String sortDirection,
			@Param("page") Page<Product> page);

	List<Product> getOnlineProductBySellerId(Integer sellerId);

	List<Product> getOnlineProductBySellerIdAndBrandId(Integer sellerId,
			Integer brandId);

	List<Integer> getProductIdsBySellerId(Integer sellerId);

	List<Product> getProductsByProdIds(@Param("idList") List<Integer> idList);

	List<Product> searchAuditProductByConditionPage(
			@Param("productCondition") ProductCondition productCondition,
			@Param("sortField") String sortField,
			@Param("sortDirection") String sortDirection,
			@Param("page") Page<Product> page);

	int getValidProductCountOfSeller(@Param("sellerId") int sellerId);
}