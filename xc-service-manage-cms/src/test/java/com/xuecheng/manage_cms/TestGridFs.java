package com.xuecheng.manage_cms;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

/**
 * @Description
 * @auther Jack
 * @create 2019-05-03 19:21
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestGridFs {

    @Autowired
    GridFsTemplate gridFsTemplate;

    //存储文件
    @Test
    public void testStore() throws FileNotFoundException {
        //输入流
        FileInputStream fileInputStream = new FileInputStream(new File("E:/FreemarkerTest/index_banner.ftl"));
        //存储文件
        GridFSFile gridFSFile = gridFsTemplate.store(fileInputStream, "轮播图文件01");
        //文件id,此id是fs.files集合的主键，通过此文件id查询fs.chunks集合files_id字段，得到文件块信息
        String fileId = gridFSFile.getId().toString();
        System.out.println(fileId);
    }
    //查询条件
    @Test
    public void testGetFile() throws IOException {
        //查询条件
        GridFSDBFile gridFSDBFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is("5cdbe700a86f8f1cf4662d62")));
        InputStream inputStream = gridFSDBFile.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream(new File("E:\\FreemarkerTest/banner.html"));
        IOUtils.copy(inputStream,fileOutputStream);
    }


}
