package com.tipster.betfair.utils.http

/**
 * Created by vbledar on 2/10/15.
 */
class JsonRpcRequest {

    Integer id = 1
    String jsonrpc = "2.0"
    String method
    Map<String, Object> params

}
