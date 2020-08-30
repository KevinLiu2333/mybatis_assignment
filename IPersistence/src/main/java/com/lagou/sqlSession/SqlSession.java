package com.lagou.sqlSession;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kevliu3
 * Date: 2020/8/23
 * Time: 4:10 PM
 *
 * @author kevliu3
 */
public interface SqlSession {
    //查询所有
    <E> List<E> selectList(String statementId, Object... params) throws Exception;

    //根据条件查询单个
    <T> T selectOne(String statementId, Object... params) throws Exception;

    //查询所有
    void update(String statementId, Object... params) throws Exception;

    //为Dao接口生成代理实现类
    <T> T getMapper(Class<?> mapperClass);

    void commit();

    void rollback();


}