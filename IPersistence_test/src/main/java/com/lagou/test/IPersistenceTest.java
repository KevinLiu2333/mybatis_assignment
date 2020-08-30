package com.lagou.test;

import com.lagou.dao.IUserDao;
import com.lagou.io.Resources;
import com.lagou.pojo.User;
import com.lagou.sqlSession.SqlSession;
import com.lagou.sqlSession.SqlSessionFactoryBuider;
import com.lagou.sqlSession.SqlSessoionFactory;
import org.dom4j.DocumentException;
import org.junit.Before;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kevliu3
 * Date: 2020/8/23
 * Time: 2:57 PM
 *
 * @author kevliu3
 */
public class IPersistenceTest {

    private IUserDao userDao;

    @Before
    public void prepare() throws PropertyVetoException, DocumentException {
        InputStream resourcesAsStream = Resources.getResourcesAsStream("sqlMapConfig.xml");
        SqlSessoionFactory sqlSessoionFactory = new SqlSessionFactoryBuider().build(resourcesAsStream);
        SqlSession sqlSession = sqlSessoionFactory.openSession();
        //获取代理对象
        userDao = sqlSession.getMapper(IUserDao.class);
    }


    @Test
    public void findAll() throws Exception {
        List<User> all = userDao.findAll();
        for (User user1 : all) {
            System.out.println(user1);
        }

    }

    @Test
    public void findByCondition() throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setId(1);
        User byCondition = userDao.findByCondition(user);
        System.out.println(byCondition);


    }

    @Test
    public void saveUser() throws Exception {
        User user = new User();
        user.setId(3);
        user.setUsername("test2");
        user.setPassword("test2");
        user.setBirthday("test2");
        userDao.saveUser(user);

    }

    @Test
    public void update() throws Exception {
        User user = new User();
        user.setId(3);
        user.setUsername("Kevinliu");
        userDao.updateUser(user);

    }


    @Test
    public void delete() throws Exception {
        User user = new User();
        user.setId(3);
        userDao.deleteUser(user);

    }


}
