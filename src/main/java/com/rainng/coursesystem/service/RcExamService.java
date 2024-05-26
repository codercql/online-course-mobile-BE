package com.rainng.coursesystem.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import com.rainng.coursesystem.model.vo.request.ExamSearchReqVO;
import com.rainng.coursesystem.model.vo.request.RcExamReqVO;
import com.rainng.coursesystem.model.vo.response.ExamDetailVO;
import com.rainng.coursesystem.model.vo.response.ResultVO;
import com.rainng.coursesystem.util.RandomNumUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
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
public class RcExamService extends BaseService {
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private RcCourseMapper rcCourseMapper;
    @Autowired
    private RcSelectCourseMapper rcSelectCourseMapper;
    @Autowired
    private ExamScoreMapper examScoreMapper;

    public ResultVO<PageInfo<ExamDetailVO>> getExamMainPage(ExamSearchReqVO vo) {
        List<ExamDetailVO> examMainPage = examMapper.getExamMainPage(vo);
        PageInfo<ExamDetailVO> pageInfo = new PageInfo<>(examMainPage);
        return result(pageInfo);
    }

    public ResultVO<String> addExam(RcExamReqVO vo) {
        RcExamEntity entity = new RcExamEntity();
        BeanUtils.copyProperties(vo, entity);
        int examId = RandomNumUtil.getRandomNum();
        entity.setExamId(examId);
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());

//        RcCourseEntity rcCourseEntity = rcCourseMapper.selectById(vo.getCourseId());
//        if (rcCourseEntity == null) {
//            return failedResult("课程不存在");
//        }
//        rcCourseEntity.setCourseExamId(examId);
//        rcCourseEntity.setUpdateTime(new Date());

        examMapper.insert(entity);
//        rcCourseMapper.updateById(rcCourseEntity);

//        if(vo.getCourseId() != null){
//            LambdaQueryWrapper<RcSelectCourseEntity> eq = new LambdaQueryWrapper<RcSelectCourseEntity>().eq(RcSelectCourseEntity::getScCourseId, vo.getCourseId());
//            List<RcSelectCourseEntity> rcSelectCourseEntities = rcSelectCourseMapper.selectList(eq);
//            rcSelectCourseEntities.forEach(sc->{
//                sc.setUpdateTime(new Date());
//                sc.setExamId(examId);
//                rcSelectCourseMapper.updateById(sc);
//            });
//        }
        return result("新增考试成功！");
    }

    public ResultVO<String> updateExam(RcExamReqVO vo) {
        RcExamEntity entity = new RcExamEntity();

        BeanUtils.copyProperties(vo,entity);
        entity.setUpdateTime(new Date());

//        RcCourseEntity rcCourseEntity = rcCourseMapper.selectById(vo.getCourseId());
//        if (rcCourseEntity == null) {
//            return failedResult("课程不存在");
//        }
//        rcCourseEntity.setCourseExamId(vo.getExamId());
//        rcCourseEntity.setUpdateTime(new Date());
        examMapper.updateById(entity);
//        rcCourseMapper.updateById(rcCourseEntity);
        return result("更新考试成功！");
    }

    public ResultVO<String> deleteExam(String examId) {
        examMapper.deleteById(examId);
        List<RcExamScoreEntity> rcExamScoreEntities = examScoreMapper.selectList(new LambdaQueryWrapper<RcExamScoreEntity>().eq(RcExamScoreEntity::getExamId, examId));
        if(CollectionUtils.isNotEmpty(rcExamScoreEntities)){
            examScoreMapper.deleteBatchIds(rcExamScoreEntities);
        }
        return result("删除考试成功！");
    }

    public ResultVO<List<ExamDetailVO>> getExamListByStudentId(Integer studentId) {
        List<RcExamScoreEntity> rcExamScoreEntities = examScoreMapper.selectList(new LambdaQueryWrapper<RcExamScoreEntity>().eq(RcExamScoreEntity::getStudentId, studentId));
        if(CollectionUtils.isEmpty(rcExamScoreEntities)){
            return result("该学生没有考试！");
        }
        List<Integer> examIdList = rcExamScoreEntities.stream().map(RcExamScoreEntity::getExamId).collect(Collectors.toList());
        ExamSearchReqVO vo = new ExamSearchReqVO();
        vo.setExamIdList(examIdList);
        return result(examMapper.getExamMainPage(vo));

    }

    public ResultVO<List<ExamDetailVO>> getExamListByTeacherId(Integer teacherId) {
        List<RcExamEntity> rcExamEntities = examMapper.selectList(new LambdaQueryWrapper<RcExamEntity>().eq(RcExamEntity::getTeacherId, teacherId));
        if(CollectionUtils.isEmpty(rcExamEntities)){
            return result("该老师没有发布考试！");
        }
        List<ExamDetailVO> res = new ArrayList<>();
        rcExamEntities.forEach(entity->{
            ExamDetailVO examDetailVO = new ExamDetailVO();
            BeanUtils.copyProperties(entity,examDetailVO);
            res.add(examDetailVO);
        });
        return result(res);
    }
}
