package com.hdu.orange_recruitment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDate;
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
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 手机号，也是账号
     */
    private String phone;

    private String password;

    private String gender;

    private String name;

    private LocalDate birth;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 1：应聘者 2：hr
     */
    private String role;
}
