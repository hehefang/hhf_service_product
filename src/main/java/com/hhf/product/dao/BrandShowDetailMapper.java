package com.hhf.product.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.hhf.common.mybatis.Page;
import com.hhf.model.product.BrandShowDetail;

public interface BrandShowDetailMapper {
	int deleteByPrimaryKey(Integer bSDId);

	int insert(BrandShowDetail record);

	int insertSelective(BrandShowDetail record);

	BrandShowDetail selectByPrimaryKey(Integer bSDId);

	int updateByPrimaryKeySelective(BrandShowDetail record);

	int updateByPrimaryKey(BrandShowDetail record);

	List<BrandShowDetail> getValidDetailsOfBrandShow(Integer brandShowId);

	List<BrandShowDetail> getBrandShowDetailsByIds(
			@Param("brandShowDetailIds") List<Integer> brandShowDetailIds);

	BigDecimal getLowestPrice(@Param("bsid") Integer bsid);

	List<BrandShowDetail> getBrandShowDetailByPage(
			@Param("cond") Map<?, ?> map,
			@Param(value = "page") Page<BrandShowDetail> page);

	void addStock(@Param("bsdId") Long bsdId, @Param("stock") Long stock);

	@Update("update t_brand_show_detail set status = #{1} where b_s_d_id = #{0} ")
	int updateStatus(Integer bsdId, String status);
	
	public BrandShowDetail getBrandShowDetailBySkuId(@Param("brandShowId")Integer brandShowId,
			@Param("skuId")Integer skuId);
	
	public List<BrandShowDetail> getBrandShowDetailsByProdId(@Param("brandShowId")Integer brandShowId,
			@Param("prodId")Integer prodId);
}