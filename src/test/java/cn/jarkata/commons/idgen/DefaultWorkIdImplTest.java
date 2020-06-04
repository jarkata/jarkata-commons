package cn.jarkata.commons.idgen;

import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultWorkIdImplTest {

    @Test
    public void getWorkId() {
        DefaultWorkIdImpl defaultWorkId = new DefaultWorkIdImpl();
        int workId = defaultWorkId.getWorkId();
        System.out.println(workId);
    }
}