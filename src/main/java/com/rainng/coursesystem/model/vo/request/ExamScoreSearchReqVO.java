package com.rainng.coursesystem.model.vo.request;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: online-course-mobile-BE
 * @description: 考试成绩查询VO
 * @author: chenqiulu
 * @create: 2024-05-26 18:38
 **/
@Data
public class ExamScoreSearchReqVO {
    @ApiModelProperty("关系id列表,选填")
    private List<Integer> idList;

    @ApiModelProperty("考试id列表,选填")
    private List<Integer> examIdList;

    @ApiModelProperty("学生id列表,选填")
    private List<Integer> studentIdList;

    @ApiModelProperty("分页参数")
    private PageInfo<ExamScoreSearchReqVO> pageParam;


}
