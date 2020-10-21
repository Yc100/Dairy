/**
 * Copyright (c) 2020
 */

package com.yc.common.entity.dairy;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yc.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Dairy
 *
 * @author YC
 * @createDate 2020/10/21 14:11
 **/
@Data
@TableName("dairy_read")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DairyReadEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private BigDecimal readCount;

	private Long dairyId;

	private Long userId;




}