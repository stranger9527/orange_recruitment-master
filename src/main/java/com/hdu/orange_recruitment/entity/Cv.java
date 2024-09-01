package com.hdu.orange_recruitment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 个人简历
 * </p>
 *
 * @author alpha
 * @since 2024-08-30
 */
@Getter
@Setter
public class Cv implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    /**
     * 离校-随时到岗-1
在校-月内到岗-2
在校-考虑机会-3
在校-暂不考虑-4

     */
    private String applicationState;

    /**
     * 初中-1、高中-2、本科-3、硕士-4、博士-5

     */
    private String qualification;

    private String basicSalary;

    private String advantage;

    private String expectation;

    private String workExperience;

    private String projectExperience;

    private String educationExperience;

    private String other;
}
