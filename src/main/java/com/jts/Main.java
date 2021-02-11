package com.jts;

import com.jts.sketches.PixelSorter;
import com.jts.websocket.GyroscopeSocket;

import javax.servlet.ServletException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.DefaultByteBufferPool;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletContainer;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import lombok.extern.slf4j.Slf4j;
import processing.core.PApplet;

@Slf4j
public class Main {

    public static void main(String[] args) throws IOException {

        String image = "pisa.jpg";
        if (args.length > 0) {
            image = args[0];
        }

        startServer(8080);
        startSketch(PixelSorter.class, image);
    }

    public static void startServer(int port) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("google.com", 80));

        PathHandler path = Handlers.path();
        Undertow undertow = Undertow.builder()
            .addHttpListener(port, "localhost")
            .addHttpListener(port, socket.getLocalAddress().getHostAddress())
            .setHandler(path)
            .build();

        undertow.start();

        final ServletContainer container = ServletContainer.Factory.newInstance();

        DeploymentInfo builder = new DeploymentInfo()
            .setClassLoader(Main.class.getClassLoader())
            .setContextPath("/")
            .setResourceManager(new ClassPathResourceManager(Main.class.getClassLoader(), Main.class.getPackage()))
            .addServletContextAttribute(WebSocketDeploymentInfo.ATTRIBUTE_NAME,
                new WebSocketDeploymentInfo()
                    .setBuffers(new DefaultByteBufferPool(true, 100))
                    .addEndpoint(GyroscopeSocket.class)
            )
            .setDeploymentName("sensors.war");

        DeploymentManager manager = container.addDeployment(builder);
        manager.deploy();
        try {
            path.addPrefixPath("/", manager.start());
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }

        log.info("Mobile app should be able to connect at: ws://{}:{}/gyroscope", socket.getLocalAddress().getHostAddress(), port);
    }

    public static void startSketch(Class clazz, String image) {
        PApplet.main(clazz, image);
    }
}
