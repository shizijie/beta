package com.shizijie.beta.rpc;

import java.io.IOException;

/**
 * @author shizijie
 * @version 2019-03-14 下午9:49
 */
public interface Server {
    public void stop();

    public void start() throws IOException;

    public void register(Class serviceInterface, Class impl);

    public boolean isRunning();

    public int getPort();
}
