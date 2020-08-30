package com.lagou.portal;

/**
 * 对UserMapper.xml进行解析
 * Created with IntelliJ IDEA.
 * User: kevliu3
 * Date: 2020/8/23
 * Time: 3:05 PM
 *
 * @author kevliu3
 */
public class MappedStatement {
    //id标识
    private String id;
    //返回值类型
    private String resultType;
    //参数类型
    private String parameterType;
    //sql语句
    private String sql;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
