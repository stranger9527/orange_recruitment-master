package com.hdu.orange_recruitment.entity.vo;

import com.hdu.orange_recruitment.entity.Progress;
import lombok.Data;

@Data
public class ProgressVo extends Progress {

    private String userCandidateName;

    private String jobTitle;

    private String qualification;

    private String basicSalary;

    private Integer enterpriseId;

    private String enterpriseName;

    private String salary;

    private String tag1;

    private String tag2;

    private String tag3;

    private String tag4;

    private String tag5;

    private String userHrName;

    private String avatar; //hr 头像

    private String position;
}
