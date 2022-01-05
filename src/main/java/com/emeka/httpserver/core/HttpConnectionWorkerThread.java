package com.emeka.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);

    private Socket socket;
public HttpConnectionWorkerThread(Socket socket){
    this.socket = socket;

}
    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try{
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();

        int _byte;
        while ((_byte = inputStream.read())>=0){
            System.out.print((char) _byte);
        }

        String html = "<html><head><title>Java HTTP Server</title></head>" +
                "<body><h1>This page you are viewing was served using my simple Java HTTP server</h1></body></html>";
        final String CRLF = "\n\r"; // 13, 10 ASCII Equivalent

        String response = "HTTP/1.1 200 OK" + CRLF + //Status Line : HTTP VERSION RESPONSE_CODE RESPONSE_MESSAGE
                "Content-Length: " + html.getBytes().length + CRLF + //Header optional
                CRLF +
                html +
                CRLF + CRLF;
        outputStream.write(response.getBytes());

LOGGER.info("Connection Processing finished");
    }catch (IOException e){
      LOGGER.error("Problem with communication",e);
    }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    }


}
