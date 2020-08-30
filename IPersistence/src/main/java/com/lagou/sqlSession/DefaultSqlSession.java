package com.lagou.sqlSession;

import com.lagou.portal.Configuration;
import com.lagou.portal.MappedStatement;

import java.lang.reflect.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kevliu3
 * Date: 2020/8/23
 * Time: 4:24 PM
 *
 * @author kevliu3
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;
    private SimpleExecutor simpleExecutor;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        this.simpleExecutor = new SimpleExecutor(configuration);
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws Exception {
        //将要去完成对simpleExecutor里的query方法调用
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        List<Object> list = simpleExecutor.query(mappedStatement, params);
        return (List<E>) list;
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws Exception {
        List<Object> objects = selectList(statementId, params);
        if (objects.size() == 1) {
            return (T) objects.get(0);
        } else {
            throw new RuntimeException("查询结果为空或返回结果过多");
        }

    }

    @Override
    public void update(String statementId, Object... params) throws Exception {
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        simpleExecutor.update(mappedStatement, params);

    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        //使用JDK动态代理来为Dao接口生成代理对象,并返回
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            /**
             * 代理对象调用接口中的任意方法,都会执行invoke方法
             * @param proxy 当前代理对象的引用
             * @param method 当前被调用方法的引用
             * @param args 传递过来的参数
             * @return 调用结果
             * @throws Throwable 异常
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //根据不同情况调用selectList或者selectOne
                //准备参数: 1: statementid :sql语句的唯一标识: namespace.id=接口全限定名.方法名
                //方法名:findAll
                String methodName = method.getName();
                //类全限定名
                String className = method.getDeclaringClass().getName();
                String statementId = className + "." + methodName;
                //准备参数2: params:args
                //获取被调用方法的返回值类型
                Type genericReturnType = method.getGenericReturnType();
                //判断是否进行了泛型类型参数化

                if (genericReturnType.getTypeName().equals("void")) {
                    update(statementId, args);
                    return null;
                }
                if (genericReturnType instanceof ParameterizedType) {
                    List<Object> objects = selectList(statementId, args);
                    return objects;
                }


                return selectOne(statementId, args);
            }
        });

        return (T) proxyInstance;
    }

    @Override
    public void commit() {

    }

    @Override
    public void rollback() {

    }
}
