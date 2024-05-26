package com.rainng.coursesystem.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.rainng.coursesystem.dao.mapper.ExamMapper;
import com.rainng.coursesystem.dao.mapper.ExamScoreMapper;
import com.rainng.coursesystem.dao.mapper.RcCourseMapper;
import com.rainng.coursesystem.dao.mapper.RcSelectCourseMapper;
import com.rainng.coursesystem.model.entity.RcCourseEntity;
import com.rainng.coursesystem.model.entity.RcExamEntity;
import com.rainng.coursesystem.model.entity.RcExamScoreEntity;
import com.rainng.coursesystem.model.entity.RcSelectCourseEntity;
import com.rainng.coursesystem.model.vo.request.ExamScoreReqVO;
import com.rainng.coursesystem.model.vo.request.ExamScoreSearchReqVO;
import com.rainng.coursesystem.model.vo.request.ExamSearchReqVO;
import com.rainng.coursesystem.model.vo.request.RcExamReqVO;
import com.rainng.coursesystem.model.vo.response.ExamDetailVO;
import com.rainng.coursesystem.model.vo.response.ExamScoreDetailVO;
import com.rainng.coursesystem.model.vo.response.ResultVO;
import com.rainng.coursesystem.util.RandomNumUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: course-system
 * @description:
 * @author: chenqiulu
 * @create: 2024-05-03 17:00
 **/
@Service
public class RcExamScoreService extends BaseService {
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private ExamScoreMapper examScoreMapper;

    public ResultVO<PageInfo<ExamScoreDetailVO>> getExamScoreMainPage(ExamScoreSearchReqVO vo) {
        List<ExamScoreDetailVO> examScoreMainPage = examScoreMapper.getExamScoreMainPage(vo);
        PageInfo<ExamScoreDetailVO> pageInfo = new PageInfo<>(examScoreMainPage);
        return result(pageInfo);
    }

    public ResultVO<String> addExamScore(List<ExamScoreReqVO> vo) {
        for (ExamScoreReqVO examScoreReqVO : vo) {
            RcExamScoreEntity entity = new RcExamScoreEntity();
            int id = RandomNumUtil.getRandomNum();
            entity.setId(id);
            entity.setExamId(examScoreReqVO.getExamId());
            entity.setStudentId(examScoreReqVO.getStudentId());
            entity.setExamScore(examScoreReqVO.getScore());
            entity.setCreateTime(new Date());
            entity.setUpdateTime(new Date());
            examScoreMapper.insert(entity);
        }

        return result("新增考试关系成功！");
    }

    public ResultVO<String> updateExamScore(int id,int score) {
        RcExamScoreEntity rcExamScoreEntity = examScoreMapper.selectById(id);
        if(rcExamScoreEntity == null){
            return result("不存在此考试关系");
        }
        rcExamScoreEntity.setExamScore(score);
        rcExamScoreEntity.setUpdateTime(new Date());
        RcExamEntity entity = new RcExamEntity();

        examScoreMapper.updateById(rcExamScoreEntity);
        return result("更新考试分数成功！");
    }

    public ResultVO<String> deleteExamScore(int id) {
        examScoreMapper.deleteById(id);
        return result("删除考试分数成功！");
    }
}
