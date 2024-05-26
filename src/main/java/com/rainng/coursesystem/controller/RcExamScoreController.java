package com.rainng.coursesystem.controller;

import com.github.pagehelper.PageInfo;
import com.rainng.coursesystem.model.vo.request.ExamScoreReqVO;
import com.rainng.coursesystem.model.vo.request.ExamScoreSearchReqVO;
import com.rainng.coursesystem.model.vo.response.ExamScoreDetailVO;
import com.rainng.coursesystem.model.vo.response.ResultVO;
import com.rainng.coursesystem.service.RcExamScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @program: online-course-mobile-BE
 * @description: 学生考试分数controller
 * @author: chenqiulu
 * @create: 2024-05-26 18:29
 **/
@Api("考试分数增删改查")
@Slf4j
@RequestMapping("/examScore")
@RestController
public class RcExamScoreController {
    @Autowired
    private RcExamScoreService rcExamScoreService;

    @ApiOperation("考试分数首页查询")
    @PostMapping("/getExamScoreMainPage")
    public ResultVO<PageInfo<ExamScoreDetailVO>> getExamScoreMainPage(@RequestBody ExamScoreSearchReqVO vo) {
        return rcExamScoreService.getExamScoreMainPage(vo);
    }

    @ApiOperation("考试分数新增")
    @PostMapping("/addExamScore")
    public ResultVO<String> addExamScore(@RequestBody @Valid List<ExamScoreReqVO> vo) {
        return rcExamScoreService.addExamScore(vo);
    }

    @ApiOperation("考试分数更新")
    @GetMapping("/updateExamScore")
    public ResultVO<String> updateExamScore(@RequestParam("id") int id,@RequestParam("score") int score) {
        return rcExamScoreService.updateExamScore(id,score);
    }

    @ApiOperation("考试分数删除")
    @GetMapping("/deleteExamScore")
    public ResultVO<String> deleteExamScore(@RequestParam("id") int id) {
        return rcExamScoreService.deleteExamScore(id);
    }

}
