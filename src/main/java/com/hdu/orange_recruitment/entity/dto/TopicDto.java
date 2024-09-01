package com.hdu.orange_recruitment.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class TopicDto {

    private Long id;

    private String name;

    private List<Integer> type;

    private String problemKeypoint;

    private Integer speciality;
}

