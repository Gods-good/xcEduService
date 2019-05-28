package com.xuecheng.manage_course;

        import com.xuecheng.framework.domain.course.CourseBase;
        import com.xuecheng.manage_course.dao.CourseBaseMapper;
        import com.xuecheng.manage_course.dao.CourseBaseRepository;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description
 * @auther Jack
 * @create 2019-05-28 17:06
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestDao {
    @Autowired
    CourseBaseRepository courseBaseRepository;
    @Autowired
    CourseBaseMapper courseBaseMapper;
    @Test
    public void testJpa(){
        CourseBase courseBase = courseBaseRepository.findOne("297e7c7c62b888f00162b8a965510001");
        System.out.println(courseBase);
    }

    @Test
    public void testMapper(){
        CourseBase courseBaseById = courseBaseMapper.findCourseBaseById("297e7c7c62b888f00162b8a965510001");
        System.out.println(courseBaseById);
    }
}
