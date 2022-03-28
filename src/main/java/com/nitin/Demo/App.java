package com.nitin.Demo;


import io.vertx.core.Vertx;

public class App{
    public static void main(String[] args){
        Vertx vertx = Vertx.vertx();
        // To make third verticle as worker, pass this into deployVerticle
        // DeploymentOptions opts = new DeploymentOptions()
        // .setWorker(true)
        // .setInstances(2);
        vertx.deployVerticle(new MainVerticle());
    }
}