package cn.jarkata.commons.utils;

import com.alibaba.fastjson2.JSON;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.Assert.*;

public class BeanUtilsTest {

    @Test
    public void copyBeans() throws IllegalAccessException {
        UserPO userPO = new UserPO();
        userPO.setUsername("test");
        userPO.setCurrent(1);
        userPO.setPageSize(10);
        userPO.setAge(123);

        List<Field> fieldList = ReflectionUtils.getFieldList(userPO.getClass());
        System.out.println(fieldList);

        UserDTO userDTO = new UserDTO();
        BeanUtils.copyBeans(userPO, userDTO);
        System.out.println(JSON.toJSONString(userDTO));
    }

    static class BasePage {
        private int current;
        private int pageSize;

        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }
    }

    static class UserPO extends BasePage {
        private String username;
        private int age;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    static class UserDTO extends BasePage {
        private String username;
        private Integer age;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}