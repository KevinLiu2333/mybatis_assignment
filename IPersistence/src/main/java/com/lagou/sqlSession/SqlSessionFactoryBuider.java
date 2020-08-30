package com.lagou.sqlSession;

import com.lagou.config.XMLConfigBuilder;
import com.lagou.portal.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: kevliu3
 * Date: 2020/8/23
 * Time: 3:14 PM
 *
 * @author kevliu3
 */
public class SqlSessionFactoryBuider {


    public SqlSessoionFactory build(InputStream in) throws PropertyVetoException, DocumentException {
        //第一,解析dom4j解析配置文件,将解析出来的内容封装到Configuration中
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        //第二,创建sqlSessionFactory对象
        Configuration configuration = xmlConfigBuilder.parseConfig(in);
        // 第二：创建sqlSessionFactory对象：工厂类：生产sqlSession:会话对象
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        return defaultSqlSessionFactory;
    }
}
