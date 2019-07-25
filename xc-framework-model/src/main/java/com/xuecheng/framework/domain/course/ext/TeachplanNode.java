package com.xuecheng.framework.domain.course.ext;

import com.xuecheng.framework.domain.course.Teachplan;
import lombok.Data;
import lombok.ToString;

import java.util.List;


@Data
@ToString
public class TeachplanNode extends Teachplan {

    List<TeachplanNode> children;

    //课程计划媒资信息
    private String mediaId;
    private String mediaFileOriginalName;

}
