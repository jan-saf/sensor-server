# Processing sketch with websocket server

This repository contains source code for a processing sketch, which displays a picture that can be edited using a [smartphone app](TODO add github repo here).

Edits implemented are: Sorting (horizontal and vertical in increments with noise), Pixel decimating (ie pixelation -> degrading of amount of pixels) and "Shuffle" (placing semirandom parts of images to another place). All those tools are controlled in some way with sensor data coming from the above mentioned smartphone app.

To run this code, you need Java 8 and Maven installed. With those tools, you can execute:
```
mvn clean package
java -jar target/TomcatEmbeddedWebSocketExample-jar-with-dependencies.jar
```
to build and run the application.

To upload your own images to this app, you can add them to src/main/resources and provide their name (including extension) as an argument of the `java -jar ...` command (or replace the name of the default picture in `Main.java`)