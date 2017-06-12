package com.qxd.birth.service.RpcService;

import com.qxd.birth.client.TestRpc;
import org.springframework.stereotype.Service;

/**
 * Created by qxd on 2017/6/12.
 */
@Service("testRpc")
public class TestRpcImpl implements TestRpc {

    @Override
    public String say(String code) {
        return "say " + code;
    }
}
