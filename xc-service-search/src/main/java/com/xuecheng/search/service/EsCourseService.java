package com.xuecheng.search.service;

import com.xuecheng.framework.domain.course.CoursePub;
import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-07-09 8:58
 **/
@Service
public class EsCourseService {

    private static  final Logger LOGGER = LoggerFactory.getLogger(EsCourseService.class);

    //索引库名称
    @Value("${xuecheng.elasticsearch.course.index}")
    private String es_index;
    //索引类型
    @Value("${xuecheng.elasticsearch.course.type}")
    private String es_type;

    //源文档字段明细
    @Value("${xuecheng.elasticsearch.course.source_field}")
    private String source_field;

    @Autowired
    RestHighLevelClient restHighLevelClient;

    /**
     * 搜索课程
     * @param page
     * @param size
     * @param courseSearchParam
     * @return
     */
    public QueryResponseResult<CoursePub> list(int page, int size, CourseSearchParam courseSearchParam) {
        if(courseSearchParam == null){
            courseSearchParam = new CourseSearchParam();
        }
        //定义搜索请求对象
        SearchRequest searchRequest = new SearchRequest(es_index);
        //指定索引类型
        searchRequest.types(es_type);
        //创建searchSourceBuilder对象
        SearchSourceBuilder searchSourceBuilder  =new SearchSourceBuilder();
        //设置包括哪些源字段,源字段明细通过配置文件获取
        String[] source_fields = source_field.split(",");
        searchSourceBuilder.fetchSource(source_fields,new String[]{});

        //设置高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        //前缀
        highlightBuilder.preTags("<font class='eslight'>");
        //后缀
        highlightBuilder.postTags("</font>");
        //设置高亮字段
        highlightBuilder.fields().add(new HighlightBuilder.Field("name"));//设置高亮字段为name

        searchSourceBuilder.highlighter(highlightBuilder);

        //创建布尔查询对象（完成综合查询）
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        //根据关键字搜索，name、description、teachplan
        if(StringUtils.isNotEmpty(courseSearchParam.getKeyword())){
            String keyword = courseSearchParam.getKeyword();
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyword, "name", "teachplan","description");
            //设置匹配比例
            multiMatchQueryBuilder.minimumShouldMatch("70%");
            //设置boost,将name字段的权限加10倍
            multiMatchQueryBuilder.field("name",10);

            //将multiMatchQueryBuilder查询加入布尔查询
            boolQueryBuilder.must(multiMatchQueryBuilder);
        }

        //过虑器
        if(StringUtils.isNotEmpty(courseSearchParam.getMt())){
            //过虑大分类
            String mt = courseSearchParam.getMt();
            boolQueryBuilder.filter(QueryBuilders.termQuery("mt",mt));

        }
        if(StringUtils.isNotEmpty(courseSearchParam.getSt())){
            //过虑小分类
            String st = courseSearchParam.getSt();
            boolQueryBuilder.filter(QueryBuilders.termQuery("st",st));

        }
        if(StringUtils.isNotEmpty(courseSearchParam.getGrade())){
            //过虑课程等级
            String grade = courseSearchParam.getGrade();
            boolQueryBuilder.filter(QueryBuilders.termQuery("grade",grade));

        }

        //分页查询
        if(page<=0){
            page = 1;
        }
        if(size<=0){
            size = 12;
        }
        int start  = (page-1)*size;
        searchSourceBuilder.from(start);//起始记录下标
        searchSourceBuilder.size(size);//每页记录数

        //将boolQueryBuilder设置到searchSourceBuilder中
        searchSourceBuilder.query(boolQueryBuilder);
        //将searchSourceBuilder设置到searchRequest中
        searchRequest.source(searchSourceBuilder);

        //开始搜索
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("search error:{}",e.getMessage());
            //返回空数据
            return new QueryResponseResult(CommonCode.FAIL,new QueryResult());
        }
        //得到匹配结果
        SearchHits hits = searchResponse.getHits();
        //总记录
        long totalHits = hits.getTotalHits();
        //得到记录
        SearchHit[] searchHits = hits.getHits();
        //返回的数据列表
        List<CoursePub> datalist = new ArrayList<>();
        for(SearchHit hit:searchHits){
            CoursePub coursePub = new CoursePub();

            //课程名称
            String name = null;
            //获取高亮字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField name_highlightField = highlightFields.get("name");
            if(name_highlightField!=null){
                Text[] fragments = name_highlightField.getFragments();
                StringBuffer stringBuffer = new StringBuffer();
                for(Text text:fragments){
                    String string = text.string();
                    stringBuffer.append(string);
                }
                //包括了高亮的名称
                name = stringBuffer.toString();
            }

            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //从源文档取出名称
            if(StringUtils.isEmpty(name)){
                name = (String) sourceAsMap.get("name");
            }
            coursePub.setName(name);
            //取出id
            String id = (String) sourceAsMap.get("id");
            coursePub.setId(id);
            //取出图片
            String pic = (String) sourceAsMap.get("pic");
            coursePub.setPic(pic);
            //取出课程等级
            String grade = (String) sourceAsMap.get("grade");
            coursePub.setGrade(grade);
            //取出课程大分类
            String mt = (String) sourceAsMap.get("mt");
            coursePub.setMt(mt);
            //取出课程小分类
            String st = (String) sourceAsMap.get("st");
            coursePub.setSt(st);
            //取出价格
            try {
                String priceString = String.valueOf(sourceAsMap.get("price"));
                if(StringUtils.isNotEmpty(priceString)){
                    float price = Float.parseFloat(priceString);
                    coursePub.setPrice(price);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //加入数据列表
            datalist.add(coursePub);

        }

        //构建搜索结果
        QueryResult<CoursePub> queryResult  = new QueryResult<>();
        queryResult.setTotal(totalHits);
        queryResult.setList(datalist);
        QueryResponseResult<CoursePub> queryResponseResult = new QueryResponseResult<>(CommonCode.SUCCESS,queryResult);


        return queryResponseResult;
    }
}
