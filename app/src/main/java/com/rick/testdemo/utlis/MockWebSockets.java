package com.rick.testdemo.utlis;

import com.orhanobut.logger.Logger;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okio.ByteString;

/**
 * package: MockWebSocket
 * author: Rick Li
 * date: 2020/5/8 10:17
 * desc:
 */
public class MockWebSockets {

    public MockWebServer mockWebServer;

    public MockWebSockets() {
        if (mockWebServer != null) {
            return;
        }
        mockWebServer = new MockWebServer();

        mockWebServer.enqueue(new MockResponse().withWebSocketUpgrade(new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                Logger.i("客户端连接创建成功!");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                Logger.i("收到新消息!");
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
                Logger.i("收到新消息Bytes!");
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                Logger.i("客户端主动关闭!");
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                Logger.i("连接关闭!");
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                Logger.i("出错了!");
            }
        }));
    }


}
