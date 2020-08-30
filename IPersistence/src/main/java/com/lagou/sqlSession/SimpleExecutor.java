package com.lagou.sqlSession;

import com.lagou.config.BoundSql;
import com.lagou.portal.Configuration;
import com.lagou.portal.MappedStatement;
import com.lagou.utils.GenericTokenParser;
import com.lagou.utils.ParameterMapping;
import com.lagou.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kevliu3
 * Date: 2020/8/23
 * Time: 4:29 PM
 */
public class SimpleExecutor implements Executor {
    private Configuration configuration;
    private Connection connection = null;


    public SimpleExecutor(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> query(MappedStatement mappedStatement, Object... params) throws Exception {
        //暂时接受一个参数
        PreparedStatement preparedStatement = getPreparedStatement(mappedStatement, params);
        //5.执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        String resultType = mappedStatement.getResultType();
        Class<?> resultTypeClass = getClassType(resultType);
        ArrayList<Object> objects = new ArrayList();
        //6.封装返回结果集
        while (resultSet.next()) {
            Object o = resultTypeClass.newInstance();
            //元数据.获取查询结果字段名称
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                //字段名
                String columnName = metaData.getColumnName(i);
                //字段值
                Object value = resultSet.getObject(columnName);
                //使用反射或者内省,根据数据库表和实体的对应关系,完成封装
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o, value);
            }
            objects.add(o);
        }
        return (List<E>) objects;
    }

    private Connection getConnection() throws SQLException {
        if (this.connection == null) {
            this.connection = configuration.getDataSource().getConnection();
        }
        return this.connection;
    }

    @Override
    public int update(MappedStatement ms, Object... params) throws Exception {
        PreparedStatement preparedStatement = getPreparedStatement(ms, params);
        int i = preparedStatement.executeUpdate();
        return i;
    }

    private Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        if (parameterType != null) {
            Class<?> aClass = Class.forName(parameterType);
            return aClass;
        }

        return null;
    }

    private PreparedStatement getPreparedStatement(MappedStatement mappedStatement, Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        //1.注册驱动,获取连接
        Connection connection = getConnection();
        //2.获取sql语句:select * from user where id = #{id} and username = #{username}
        //转换sql语句:select * from user where id = ? and username = ?,转换过程中,还需要对#{}里的值进行解析存储
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);
        //3.获取预处理对象: preparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
        //4.设置参数
        //获取到了参数的全路径
        String parameterType = mappedStatement.getParameterType();
        Class<?> parameterClass = getClassType(parameterType);
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();
            //反射
            Field declaredField = parameterClass.getDeclaredField(content);
            //暴力访问
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);
            preparedStatement.setObject(i + 1, o);
        }
        return preparedStatement;
    }

    /**
     * 完成对#{}的解析工作: 1.将#{}使用?进行代替,2.解析出#{}里面的值进行存储
     *
     * @param sql sql
     * @return 解析sql
     */
    private BoundSql getBoundSql(String sql) {
        //标记处理类:配置标记解析器来完成对占位符的解析处理工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        //解析出来的sql
        String parseSql = genericTokenParser.parse(sql);
        //#{}里面解析出来的参数名称
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        BoundSql boundSql = new BoundSql(parseSql, parameterMappings);
        return boundSql;
    }
}
