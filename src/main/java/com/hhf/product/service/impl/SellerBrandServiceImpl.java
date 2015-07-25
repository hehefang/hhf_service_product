/**
 * Copyright (c)2015-? by www.hehefang.com. All rights reserved.
 * 
 */
package com.hhf.product.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhf.common.mybatis.Page;
import com.hhf.common.util.DateUtils;
import com.hhf.constants.product.ProductConstants.SellerBrand$Status;
import com.hhf.model.product.Brand;
import com.hhf.model.product.SellerBrand;
import com.hhf.product.dao.SellerBrandMapper;
import com.hhf.service.product.ISellerBrandService;
import com.hhf.service.seller.ISellerLoginService;
import com.hhf.service.seller.ISellerService;

/**
 * 卖家品牌服务
 * 
 * @author xuzunyuan
 * @date 2015年2月5日
 */
@Service("sellerBrandService")
public class SellerBrandServiceImpl implements ISellerBrandService {
	@Autowired
	SellerBrandMapper sellerBrandMapper;

	@Autowired
	ISellerLoginService sellerLoginService;

	@Autowired
	ISellerService sellerService;

	@Override
	public SellerBrand getSellerBrandById(int id) {
		return sellerBrandMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<SellerBrand> getSellerBrandList(int sellerId) {
		return sellerBrandMapper.selectSellerBrandList(sellerId);
	}

	@Override
	public List<Brand> getValidBrandListOfSeller(int sellerId) {
		return sellerBrandMapper.selectValidBrandOfSeller(sellerId);
	}

	@Override
	public int applySellerBrand(SellerBrand sellerBrand) {
		// 校验卖家是否已申请该品牌
		if (existSellerBrand(sellerBrand.getSellerId(),
				sellerBrand.getBrandId()))
			return -1;

		sellerBrand.setStatus(SellerBrand$Status.WAIT_AUDIT); // 初始状态待审核
		sellerBrand.setSubmitDate(DateUtils.currentDate());

		sellerBrand.setLoginName(sellerLoginService.getLoginBySellerId(
				sellerBrand.getSellerId()).getLoginName());
		sellerBrand.setCoName(sellerService.getSellerById(
				sellerBrand.getSellerId()).getCoName());

		if (sellerBrandMapper.insert(sellerBrand) == 0) {
			return 0;
		}

		return sellerBrand.getSellerBrandId();
	}

	@Override
	public int updateApplySellerBrand(SellerBrand sellerBrand) {
		sellerBrand.setStatus(SellerBrand$Status.WAIT_AUDIT); // 初始状态待审核
		sellerBrand.setSubmitDate(DateUtils.currentDate());

		return sellerBrandMapper.updateByPrimaryKeySelective(sellerBrand);
	}

	@Override
	public Page<SellerBrand> queryWaitAuditApplyByPage(
			Map<String, Object> cond, int... page) {
		Page<SellerBrand> p = new Page<SellerBrand>();

		if (page != null) {
			p.setCurrentPageNo(page[0]);

			if (page.length > 1) {
				p.setPageSize(1);
			}
		}

		List<SellerBrand> result = sellerBrandMapper
				.queryWaitAurditApplyByPage(cond, p);
		p.setResult(result);

		return p;
	}

	@Override
	public int passApply(String auditor, String opinion, int... sellerBrandId) {
		int ret = 1;

		for (int id : sellerBrandId) {
			if (sellerBrandMapper.updateAuditStatus(id,
					SellerBrand$Status.VALID, DateUtils.currentDate(), auditor,
					opinion) == 0)
				ret = 0;
		}

		return ret;
	}

	@Override
	public int rejectApply(String auditor, String opinion, int... sellerBrandId) {
		int ret = 1;

		for (int id : sellerBrandId) {
			if (sellerBrandMapper.updateAuditStatus(id,
					SellerBrand$Status.REJECT, DateUtils.currentDate(),
					auditor, opinion) == 0)
				ret = 0;
		}

		return ret;
	}

	@Override
	public boolean existSellerBrand(int sellerId, int brandId) {
		return sellerBrandMapper.selectBySellerAndBrand(sellerId, brandId) != null;
	}

}
