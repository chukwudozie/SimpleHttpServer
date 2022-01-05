package com.emeka.httpserver.core;

import com.emeka.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener extends Thread {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListener.class);

    private int port;
    private String webroot;
    private ServerSocket serverSocket;

    public ServerListener(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(this.port);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getWebroot() {
        return webroot;
    }

    public void setWebroot(String webroot) {
        this.webroot = webroot;
    }

    @Override
    public void run() {

        try {
            while(serverSocket.isBound() && !serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                LOGGER.info("* Connection Accepted: " + socket.getInetAddress());
                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
                workerThread.start();
            }
        } catch (IOException e) {
            LOGGER.error("Problem with setting socket",e);
        }finally {
            if(serverSocket != null){
                try {
                    serverSocket.close();
                }catch (IOException e){

                }
            }
        }


    }
}
