package cn.jarkata.commons.idcreator;

import cn.jarkata.commons.idcreator.impl.SnowflakeIdCreator;

public class IdFactory {

    private static final IdCreator idCreator = new SnowflakeIdCreator();

    public static long createId(long workId) {
        return idCreator.createId(workId);
    }
}
