package com.xuecheng.manage_course.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-06-30 11:47
 **/
@Service
public class CourseService {

    @Autowired
    CourseBaseMapper courseBaseMapper;
    @Autowired
    CourseBaseRepository courseBaseRepository;
    @Autowired
    CourseMarketRepository courseMarketRepository;
    @Autowired
    CoursePicRepository coursePicRepository;

    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    TeachplanMapper teachplanMapper;

    @Autowired
    TeachplanRepository teachplanRepository;

    //查询课程列表
    public QueryResponseResult<CourseInfo> findCourseList(int page,int size,CourseListRequest courseListRequest){
        if(page<0){
            page = 1;
        }
        if(size<=0){
            size = 10;
        }
        //调用dao
        //设置分页参数
        PageHelper.startPage(page,size);
        Page<CourseInfo> courseListPage = courseBaseMapper.findCourseListPage(courseListRequest);
        //列表
        List<CourseInfo> list = courseListPage.getResult();
        //总数
        long total = courseListPage.getTotal();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(list);
        queryResult.setTotal(total);
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }

    //分类查询
    public CategoryNode findCategoryList(){
        CategoryNode categoryNode = categoryMapper.selectList();
        return categoryNode;
    }

    //新增课程
    public AddCourseResult add(CourseBase courseBase){
        //校验非空字段
        if(courseBase == null){
            ExceptionCast.cast(CommonCode.INVLIDATE);
        }
        if(StringUtils.isEmpty(courseBase.getName())){
            //抛出异常..

        }
        //课程状态默认为制作中
        courseBase.setStatus("202001");
        CourseBase save = courseBaseRepository.save(courseBase);
        if(save!=null && StringUtils.isNotEmpty(save.getId())){
            return new AddCourseResult(CommonCode.SUCCESS,save.getId());
        }

        return new AddCourseResult(CommonCode.FAIL,null);
    }

    //查询课程计划
    public TeachplanNode findTeachplanList(String courseId){
        TeachplanNode teachplanNode = teachplanMapper.selectList(courseId);
        return teachplanNode;
    }



    /**
     * 获取根结点,如果根结点找不到，创建根结点
     * @param courseId 课程id
     * @return 根结点id
     */
    private String getTeanplanRoot(String courseId){
        //根据课程id和parentid(0)查询teachplan
        List<Teachplan> teachplanList = teachplanRepository.findByCourseidAndParentid(courseId, "0");
        if(teachplanList == null || teachplanList.size()==0){
            CourseBase one = courseBaseRepository.findOne(courseId);
            if(one == null){
                return null;
            }
            //创建根结点，向teachplan表添加一条记录
            Teachplan teachplan = new Teachplan();
            teachplan.setPname(one.getName());//根结点的名称为课程名称
            teachplan.setParentid("0");
            teachplan.setGrade("1");
            teachplan.setCourseid(courseId);
            teachplan.setStatus("0");
            Teachplan save = teachplanRepository.save(teachplan);
            return save.getId();

        }
        Teachplan teachplan = teachplanList.get(0);
        return teachplan.getId();

    }
    /**
     * 添加课程计划
     * 业务流程：
     * 1、添加一级结点
     * 1）确定根结点
     * 页面传入parentid为空， 处理根结点：
     *   找到一级结点所属的课程的根结点
     *   如果该课程为新课程那么根结点为空，自动创建课程根结点
     * 2）确定结点的层级 grade
     * 根据父结点来确定，如果该结点的grade为2，添加的新结点为3
     *
     * 2、添加二级结点
     * 1）确定根结点
     *   页面一定传入parentid
     * 2）确定结点的层级 grade
     *    根据父结点来确定，如果该结点的grade为2，添加的新结点为3；如果该结点的grade为1，添加的新结点为2
     *
     *
     * @param teachplan
     * @return
     */
    public ResponseResult addTeachplan(Teachplan teachplan){
        if(teachplan == null){
            ExceptionCast.cast(CommonCode.INVLIDATE);
        }
        //得到课程 id
        String courseid = teachplan.getCourseid();

        //设置parentid
        String parentid = teachplan.getParentid();
        if(StringUtils.isEmpty(parentid)){
            //如果传入parentid为空说明要添加一级结点
            //先找到根结点 ，根据课程id和parentid查询teachplan表
            parentid = this.getTeanplanRoot(courseid);
        }
        if(StringUtils.isEmpty(parentid)){
            ExceptionCast.cast(CourseCode.COURSE_ADDTEACHPLAN_PARENTIDISNULL);
        }
        //根据parentid查询结点
        Teachplan parentNode = teachplanRepository.findOne(parentid);
        //父结点的层级
        String parent_grade = parentNode.getGrade();
        //子结点的层级
        String grade = null;
        if(parent_grade.equals("1")){
            grade = "2";
        }else  if(parent_grade.equals("2")){
            grade = "3";
        }
        teachplan.setParentid(parentid);
        teachplan.setGrade(grade);
        //状态为未发布
        teachplan.setStatus("0");
        teachplan.setCourseid(courseid);

        //向数据库保存课程计划
        Teachplan save = teachplanRepository.save(teachplan);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //保存课程图片到course_pic
    @Transactional
    public ResponseResult saveCoursePic(String courseId, String pic) {
        CoursePic one = coursePicRepository.findOne(courseId);

        if(one!=null){//保存图片
            one.setPic(pic);
            coursePicRepository.save(one);
        }else{
            CoursePic coursePic = new CoursePic();
            coursePic.setCourseid(courseId);
            coursePic.setPic(pic);
            CoursePic save = coursePicRepository.save(coursePic);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //根据课程id查询课程图片
    public CoursePic findCoursepicList(String courseId) {
        CoursePic coursePic = coursePicRepository.findOne(courseId);
        return coursePic;
    }
    //删除课程图片
    @Transactional
    public ResponseResult deleteCoursePic(String courseId) {
        //删除课程图片信息，如果成功返回值大于0
        long result = coursePicRepository.deleteByCourseid(courseId);
        if(result>0){
            return new ResponseResult(CommonCode.SUCCESS);
        }else{
            return new ResponseResult(CommonCode.FAIL);
        }

    }
    //根据课程id查询课程全部信息，此信息用于静态化
    public CourseView getCoruseView(String courseId) {
        CourseView courseView = new CourseView();
        CourseBase courseBase = courseBaseRepository.findOne(courseId);
        if (courseBase == null){
            return courseView;
        }
        //图片
        CoursePic coursePic = coursePicRepository.findOne(courseId);
        //营销信息
        CourseMarket courseMarket = courseMarketRepository.findOne(courseId);
        //课程计划
        TeachplanNode teachplanNode = teachplanMapper.selectList(courseId);
        courseView.setCourseBase(courseBase);
        courseView.setCourseMarket(courseMarket);
        courseView.setCoursePic(coursePic);
        courseView.setTeachplanNode(teachplanNode);
        return courseView;
    }
}
