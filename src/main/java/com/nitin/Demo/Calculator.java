package com.nitin.Demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
// import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class Calculator extends AbstractVerticle{

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        super.start();
        Router router = Router.router(vertx);

        vertx.createHttpServer()
             .requestHandler(router)
             .listen(8091)
             .onSuccess(ok -> {
                 System.out.println("Started Properly");
                 startPromise.complete();
             })
             .onFailure(startPromise::fail);

        // HttpServer httpServer = vertx.createHttpServer();
        router.get("/add").handler(BodyHandler.create()).handler(this::add);
        router.get("/sub").handler(BodyHandler.create()).handler(this::sub);

        router.get("/mul/:num1/:num2").handler(routingContext -> {
            String n1 = routingContext.request().getParam("num1");
            String n2 = routingContext.request().getParam("num2");
            Double d1 = Double.valueOf(n1);
            Double d2 = Double.valueOf(n2);
            HttpServerResponse response = routingContext.response();
            response.setChunked(true);
            response.write(String.valueOf(d1*d2));
            response.end();
        });

        router.get("/div/:num1/:num2").handler(routingContext -> {
            String n1 = routingContext.request().getParam("num1");
            String n2 = routingContext.request().getParam("num2");
            Double d1 = Double.valueOf(n1);
            Double d2 = Double.valueOf(n2);
            HttpServerResponse response = routingContext.response();
            response.setChunked(true);
            response.write(String.valueOf(d1/d2));
            response.end();
        });

        router.get("/rem/:num1/:num2").handler(routingContext -> {
            String n1 = routingContext.request().getParam("num1");
            String n2 = routingContext.request().getParam("num2");
            Double d1 = Double.valueOf(n1);
            Double d2 = Double.valueOf(n2);
            HttpServerResponse response = routingContext.response();
            response.setChunked(true);
            response.write(String.valueOf(d1%d2));
            response.end();
        });

        router.get("/pow/:num1/:num2").handler(routingContext -> {
            String n1 = routingContext.request().getParam("num1");
            String n2 = routingContext.request().getParam("num2");
            Double d1 = Double.valueOf(n1);
            Double d2 = Double.valueOf(n2);
            HttpServerResponse response = routingContext.response();
            response.setChunked(true);
            response.write(String.valueOf(Math.pow(d1, d2)));
            response.end();
        });

        // httpServer.requestHandler(router).listen(8091);
    }

    public void add(RoutingContext routingContext){
        JsonObject obj = routingContext.getBodyAsJson();
        System.out.print(obj.toString());
        vertx.eventBus().request("add", obj, reply -> {
            routingContext.request().response().end((String)reply.result().body());
        });
    }

    public void sub(RoutingContext routingContext){
        JsonObject obj = routingContext.getBodyAsJson();
        System.out.print(obj.toString());
        vertx.eventBus().request("sub", obj, reply -> {
            routingContext.request().response().end((String)reply.result().body());
        });
    }
    
}
