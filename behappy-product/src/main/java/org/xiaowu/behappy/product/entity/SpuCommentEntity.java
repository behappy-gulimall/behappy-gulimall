package org.xiaowu.behappy.product.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 商品评价
 *
 * @author xiaowu
 * @email wangxiaowu950330@foxmail.com
 * @date 2023-05-27 15:26:19
 */
@Data
@TableName("pms_spu_comment")
public class SpuCommentEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * sku_id
	 */
	private Long skuId;
	/**
	 * spu_id
	 */
	private Long spuId;
	/**
	 * 商品名字
	 */
	private String spuName;
	/**
	 * 会员昵称
	 */
	private String memberNickName;
	/**
	 * 星级
	 */
	private Integer star;
	/**
	 * 会员ip
	 */
	private String memberIp;
	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
	/**
	 * 显示状态[0-不显示，1-显示]
	 */
	private Integer showStatus;
	/**
	 * 购买时属性组合
	 */
	private String spuAttributes;
	/**
	 * 点赞数
	 */
	private Integer likesCount;
	/**
	 * 回复数
	 */
	private Integer replyCount;
	/**
	 * 评论图片/视频[json数据；[{type:文件类型,url:资源路径}]]
	 */
	private String resources;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 用户头像
	 */
	private String memberIcon;
	/**
	 * 评论类型[0 - 对商品的直接评论，1 - 对评论的回复]
	 */
	private Integer commentType;

}
