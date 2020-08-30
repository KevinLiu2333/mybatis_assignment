package com.lagou.config;

import com.lagou.portal.Configuration;
import com.lagou.portal.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kevliu3
 * Date: 2020/8/23
 * Time: 3:56 PM
 *
 * @author kevliu3
 */
public class XMLMapperBuilder {
    private Configuration configuration;


    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream inputStream) throws DocumentException {
        Document document = new SAXReader().read(inputStream);
        //mapper
        Element rootElement = document.getRootElement();
        List<Element> total = new ArrayList<>();
        total.addAll(rootElement.selectNodes("//select"));
        total.addAll(rootElement.selectNodes("//insert"));
        total.addAll(rootElement.selectNodes("//update"));
        total.addAll(rootElement.selectNodes("//delete"));
        String namespace = rootElement.attributeValue("namespace");
        for (Element element : total) {
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String parameterType = element.attributeValue("parameterType");
            String sqlText = element.getTextTrim();
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setResultType(resultType);
            mappedStatement.setSql(sqlText);
            String key = namespace + "." + id;
            configuration.getMappedStatementMap().put(key, mappedStatement);
        }
    }
}
