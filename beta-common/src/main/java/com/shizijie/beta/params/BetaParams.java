package com.shizijie.beta.params;


import org.springframework.stereotype.Component;

/**
 * @author shizijie
 * @version 2018-12-09 上午10:56
 */
@Component
public interface BetaParams {
    int MAX_POOL_SIZE=Runtime.getRuntime().availableProcessors()*2;

    String TOKEN="token";

    String ROOT_PATH="server.servlet-path";

    String SESSION_TIME_OUT="filter.sessionTimeOut";

    String FILTER_ON_OFF="filter.onOff";

    String FILTER_PASS_URL="filter.passUrl";
}
