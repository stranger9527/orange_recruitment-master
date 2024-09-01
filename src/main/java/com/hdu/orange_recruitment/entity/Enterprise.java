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
public class Enterprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    /**
     * 公司规模
1：0-20人
2：20-99
3：100-499
4：500-999
5：1000-9999
6：10000+
     */
    private String staffSize;

    /**
     * 行业
     */
    private String idustry;

    private String address;
}
