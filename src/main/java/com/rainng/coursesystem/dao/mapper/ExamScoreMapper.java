package com.rainng.coursesystem.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rainng.coursesystem.model.entity.RcExamScoreEntity;
import com.rainng.coursesystem.model.vo.request.ExamScoreSearchReqVO;
import com.rainng.coursesystem.model.vo.response.ExamScoreDetailVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: course-system
 * @description:
 * @author: chenqiulu
 * @create: 2024-05-03 16:59
 **/
@Mapper
public interface ExamScoreMapper extends BaseMapper<RcExamScoreEntity> {

    List<ExamScoreDetailVO> getExamScoreMainPage(ExamScoreSearchReqVO vo);

}
