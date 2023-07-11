package org.xiaowu.behappy.api.product.vo;

import lombok.Data;

/**
 * spu信息介绍
 * @author xiaowu
 */
@Data
public class SpuInfoDescEntityVo {

	/**
	 * 商品id
	 */
	private Long spuId;
	/**
	 * 商品介绍
	 */
	private String decript;

}
