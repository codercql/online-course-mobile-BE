package com.rainng.coursesystem.model.vo.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: online-course-mobile-BE
 * @description:
 * @author: chenqiulu
 * @create: 2024-05-26 18:53
 **/
@Data
public class ExamScoreReqVO {
    @NotNull(message = "学生id不能为空")
    private int studentId;
    @NotNull(message = "考试id不能为空")
    private int examId;

    private int score;
}
