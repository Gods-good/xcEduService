package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.Teachplan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description
 * @auther Jack
 * @create 2019-05-28 17:09
 */
public interface TeachplanRepository extends JpaRepository<Teachplan,String> {
    List<Teachplan> findByCourseidAndParentid(String courseId, String parentId);

}
