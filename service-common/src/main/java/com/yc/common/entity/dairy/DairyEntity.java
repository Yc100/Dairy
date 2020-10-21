/**
 * Copyright (c) 2020
 */

package com.yc.common.entity.dairy;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yc.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * Dairy
 *
 * @author YC
 * @createDate 2020/10/21 14:11
 **/
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("dairy")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DairyEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String title;

	private String dairyDescribe;

	private String mainText;

	private Long userId;

	private Date updateDate;

	private String deleted;

	@TableField(exist = false)
	private String nickName;

	@TableField(exist = false)
	private String headUrl;

}