package client;

import client.console.ConsoleCommandManager;
import client.console.GroupMessageResponseHandler;
import client.console.LoginConsoleCommand;
import client.handler.*;
import codec.PacketCodecHandler;
import codec.PacketDecoder;
import codec.PacketEncoder;
import codec.Spliter;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import idle.ConnectionWatchdog;
import io.netty.bootstrap.Bootstrap;

import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;

import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.HashedWheelTimer;
import util.SessionUtil;


import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * @author duxiaoming
 */
public class ImClient {
    private static final int MAX_RETRY = 5;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;

    protected final HashedWheelTimer timer = new HashedWheelTimer();

    private Bootstrap bootstrap;


    public static void main(String[] args) {

        new ImClient().connect(HOST, PORT, MAX_RETRY);
    }

    private void connect(String host, int port, int retry) {

        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        bootstrap = new Bootstrap();



        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true);

        final ConnectionWatchdog watchdog = new ConnectionWatchdog(bootstrap, timer, port,host, true) {

            @Override
            public ChannelHandler[] handlers() {
                return new ChannelHandler[] {
                        this,
                        new Spliter(),
                        new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS),
                        PacketCodecHandler.INSTANCE,
                        LoginResponseHandler.INSTANCE,
                        HeartBeatTimerHandler.INSTANCE,
                        new IMClientHandler()
                };
            }
        };
        bootstrap.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(watchdog.handlers());
            }
        });
//        boot.connect(host,port);
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 连接成功，启动控制台线程……");
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("terminal-pool-%d").build();
        ExecutorService ex = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        Scanner sc = new Scanner(System.in);


        ex.execute(() -> {
            while (!Thread.interrupted()) {
                if (!SessionUtil.hasLogin(channel)) {
                    loginConsoleCommand.exec(sc, channel);
                } else {
                    consoleCommandManager.exec(sc, channel);

                }
            }
        });
    }
}
