package cn.jarkata.commons.idcreator;

import org.junit.Assert;
import org.junit.Test;

public class IdFactoryTest {

    @Test
    public void createId() {
        long id = IdFactory.createId(1);
        Assert.assertTrue(id > 0);
    }

    @Test
    public void testCreateId() {
    }
}