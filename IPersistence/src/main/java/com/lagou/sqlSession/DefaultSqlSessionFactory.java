package com.lagou.sqlSession;

import com.lagou.portal.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: kevliu3
 * Date: 2020/8/23
 * Time: 4:32 PM
 *
 * @author kevliu3
 */
public class DefaultSqlSessionFactory implements SqlSessoionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
