package com.nitin.Demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public class HelperVerticle extends AbstractVerticle{

    @Override
    public void start() throws Exception {
        vertx.eventBus().consumer("add", msg -> {
            add(msg);
        });

        vertx.eventBus().consumer("sub", msg -> {
            sub(msg);
        });
    }

    private void sub(Message<Object> msg) {
        JsonObject request = (JsonObject) msg.body();
		Double num1 = Double.valueOf(request.getString("num1"));
		Double num2 = Double.valueOf(request.getString("num2"));
        msg.reply(String.valueOf(num1-num2));
    }

    private void add(Message<Object> msg) {
        JsonObject request = (JsonObject) msg.body();
		Double num1 = Double.valueOf(request.getString("num1"));
		Double num2 = Double.valueOf(request.getString("num2"));
        msg.reply(String.valueOf(num1+num2));
    }
    
}
