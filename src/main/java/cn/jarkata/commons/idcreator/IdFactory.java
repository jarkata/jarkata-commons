package cn.jarkata.commons.idcreator;

import cn.jarkata.commons.idcreator.impl.SnowflakeIdCreator;
import cn.jarkata.commons.utils.DateUtils;

import java.time.LocalDate;
import java.util.Objects;

public class IdFactory {

    private static final IdCreator idCreator = new SnowflakeIdCreator();

    public static long createId(long workId) {
        return idCreator.createId(workId);
    }

    /**
     * 根据节点序号，创建指定长度的ID，ID长度必须大于20位
     *
     * @param workId 节点编号
     * @param len    ID长度
     * @return 日期开头的指定ID
     */
    public static String createId(long workId, int len) {
        if (len < 27) {
            throw new IllegalArgumentException("len 必须大于27");
        }
        StringBuilder creatorId = new StringBuilder(Objects.toString(idCreator.createId(workId)));
        int createIdLen = creatorId.length();
        if (createIdLen > len - 8) {
            creatorId = new StringBuilder(creatorId.substring(len - 8 - createIdLen));
        } else if (createIdLen < len - 8) {
            int padding = len - 8 - createIdLen;
            for (int padIndex = 0; padIndex < padding; padIndex++) {
                creatorId.insert(0, "0");
            }
        }
        return DateUtils.toBasicDate(LocalDate.now()) + creatorId;
    }
}
