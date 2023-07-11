package org.xiaowu.behappy.api.product.vo;

import lombok.Data;

/**
 * @author xiaowu
 */
@Data
public class SkuImagesEntityVo {

	/**
	 * id
	 */
	private Long id;
	/**
	 * sku_id
	 */
	private Long skuId;
	/**
	 * 图片地址
	 */
	private String imgUrl;
	/**
	 * 排序
	 */
	private Integer imgSort;
	/**
	 * 默认图[0 - 不是默认图，1 - 是默认图]
	 */
	private Integer defaultImg;

}
