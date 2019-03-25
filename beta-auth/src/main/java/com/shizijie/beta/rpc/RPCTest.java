package com.shizijie.beta.rpc;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author shizijie
 * @version 2019-03-14 下午10:01
 */
public class RPCTest {
    public static void main(String[] args) throws IOException {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Server serviceServer = new ServiceCenter(8088);
                    serviceServer.register(HellowService.class, HellowServiceImpl.class);
                    serviceServer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        HellowService service = RPCClient.getRemoteProxyObj(HellowService.class, new InetSocketAddress("localhost", 8088));
        System.out.println(service.say("test"));
    }
}
