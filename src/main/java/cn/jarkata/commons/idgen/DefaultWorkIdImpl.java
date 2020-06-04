package cn.jarkata.commons.idgen;

import cn.jarkata.commons.utils.NetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class DefaultWorkIdImpl implements WorkIdFactory {

    private final Logger logger = LoggerFactory.getLogger(DefaultWorkIdImpl.class);

    @Override
    public int getWorkId() {
        try {
            String inet4Address = NetUtils.getInet4Address();
            if (Objects.isNull(inet4Address)) {
                return -1;
            }
            int index = inet4Address.lastIndexOf(".");
            if (index <= 0) {
                return 1;
            }
            return Integer.parseInt(inet4Address.substring(index + 1));
        } catch (Exception e) {
            logger.error("根据IP获取workId异常", e);
        }
        return 1;
    }
}
