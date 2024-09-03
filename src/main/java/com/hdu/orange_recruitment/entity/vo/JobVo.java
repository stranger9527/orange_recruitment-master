package com.hdu.orange_recruitment.entity.vo;

import com.hdu.orange_recruitment.entity.Job;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class JobVo extends Job {

    private Integer enterpriseId;

    private String enterpriseName;

    private String avatar;

    private String position;
}
