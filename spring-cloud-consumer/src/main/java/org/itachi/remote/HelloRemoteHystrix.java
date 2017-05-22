package org.itachi.remote;

/**
 * Created by itachi on 2017/5/22.
 * User: itachi
 * Date: 2017/5/22
 * Time: 21:12
 */
public class HelloRemoteHystrix implements HelloRemote {
    @Override
    public String hello(String name) {
        return "hello " + name + ", this message send failed!";
    }
}
