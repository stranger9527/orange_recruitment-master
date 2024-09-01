package com.hdu.orange_recruitment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author alpha
 * @since 2024-08-30
 */
@Getter
@Setter
@TableName("enterprise_staff")
public class EnterpriseStaff implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer enterpriseId;

    private Integer userId;

    /**
     * 职位
     */
    private String position;
}
