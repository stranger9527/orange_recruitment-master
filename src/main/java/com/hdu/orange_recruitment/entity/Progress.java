package com.hdu.orange_recruitment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
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
public class Progress implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userCandidateId;

    private Integer userHrId;

    private Integer jobId;

    /**
     * 1：收藏 2：沟通过 3：简历已投递 4：待面试 5：待评价  6：已发offe 7：确认入职
     */
    private String status;

    private LocalDateTime interviewTime;

    private String interviewAddress;
}
