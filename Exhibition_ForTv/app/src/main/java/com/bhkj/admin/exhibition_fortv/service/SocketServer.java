package com.bhkj.admin.exhibition_fortv.service;

import android.util.Log;

import com.bhkj.admin.exhibition_fortv.utils.WebConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2018/10/16.
 */

public class SocketServer {
    private boolean isEnable;
    private final WebConfig webConfig;//配置信息类
    private final ExecutorService threadPool;//线程池
    private ServerSocket socket;

    public SocketServer(WebConfig webConfig) {
        this.webConfig = webConfig;
        threadPool = Executors.newCachedThreadPool();
    }
    /**
     * 开启server
     */
    public void startServerAsync() {
        isEnable=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                doProcSync();
            }
        }).start();
    }

    /**
     * 关闭server
     */
    public void stopServerAsync() throws IOException {
        if (!isEnable){
            return;
        }
        isEnable=true;
        socket.close();
        socket=null;
    }
    private void doProcSync() {
        try {
            InetSocketAddress socketAddress=new InetSocketAddress(webConfig.getPort());
            socket=new ServerSocket();
            socket.bind(socketAddress);
            while (isEnable){
                final Socket remotePeer= socket.accept();
                threadPool.submit(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("SocketServer", remotePeer.getRemoteSocketAddress().toString());
//
//      onAcceptRemotePeer(remotePeer);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void onAcceptRemotePeer(Socket remotePeer) {
        try {
            remotePeer.getOutputStream().write("connected successful".getBytes());//告诉客户端连接成功
            // 从Socket当中得到InputStream对象
            InputStream inputStream = remotePeer.getInputStream();
            byte buffer[] = new byte[1024 * 4];
            int temp = 0;
            // 从InputStream当中读取客户端所发送的数据
            while ((temp = inputStream.read(buffer)) != -1) {
                Log.d("SocketServer", new String(buffer, 0, temp, "UTF-8"));
                remotePeer.getOutputStream().write(buffer,0,temp);//把客户端传来的消息发送回去
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
