package com.nitin.Demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;

public class MainVerticle extends AbstractVerticle{

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        CompositeFuture.all(
            deployVerticle(Calculator.class.getName()),
            deployVerticle(HelperVerticle.class.getName())
            ).onComplete(f ->{
                if(f.succeeded()){
                    System.out.println("Succesfully deployed verticles");
                    startPromise.complete();
                }
                else{
                    startPromise.fail(f.cause());
                }
            });
    }

    private Future<Void> deployVerticle(String name) {
        Future<Void> val = Future.future(handler -> {
            vertx.deployVerticle(name, event -> {
                if(event.succeeded()){
                    handler.complete();
                }
                else{
                    handler.fail(event.cause());
                }
            });
        });
        return val;
    }
    
}
