package com.hdu.orange_recruitment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userHrId;

    private String title;

    /**
     * 前端下拉框选择日薪（200/天）月薪（6000/月）
     */
    private String salary;

    private String description;

    private String address;

    private String educationRequirement;

    private String tag1;

    private String tag2;

    private String tag3;

    private String tag4;

    private String tag5;
}
