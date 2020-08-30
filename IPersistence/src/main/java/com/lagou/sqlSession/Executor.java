package com.lagou.sqlSession;

import com.lagou.portal.MappedStatement;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kevliu3
 * Date: 2020/8/23
 * Time: 4:28 PM
 *
 * @author kevliu3
 */
public interface Executor {
    <E> List<E> query(MappedStatement mappedStatement, Object... params) throws Exception;

    int update(MappedStatement ms, Object... params) throws Exception;


}
