package com.lagou.portal;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kevliu3
 * Date: 2020/8/23
 * Time: 3:08 PM
 *
 * @author kevliu3
 */
public class Configuration {

    private DataSource dataSource;

    /**
     * key:statementid value:封装好的mappedstatement对象
     */
    private Map<String, MappedStatement> mappedStatementMap = new HashMap<>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }
}

