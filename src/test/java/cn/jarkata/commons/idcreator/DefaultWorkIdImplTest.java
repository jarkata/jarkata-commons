package cn.jarkata.commons.idcreator;

import cn.jarkata.commons.idcreator.impl.DefaultWorkIdImpl;
import org.junit.Test;

public class DefaultWorkIdImplTest {

    @Test
    public void getWorkId() {
        DefaultWorkIdImpl defaultWorkId = new DefaultWorkIdImpl();
        long workId = defaultWorkId.createWorkId();
        System.out.println(workId);
    }
}