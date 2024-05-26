package com.rainng.coursesystem.model.vo.response;

import com.rainng.coursesystem.model.entity.RcExamScoreEntity;
import lombok.Data;

/**
 * @program: course-system
 * @description:
 * @author: chenqiulu
 * @create: 2024-05-11 21:32
 **/
@Data
public class ExamScoreDetailVO extends RcExamScoreEntity {

    private String examName;

    private String studentName;
}
