package client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import protocol.request.HeartBeatRequestPacket;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@ChannelHandler.Sharable
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {

    public static final HeartBeatTimerHandler INSTANCE = new HeartBeatTimerHandler();

    private HeartBeatTimerHandler() {

    }

    private static final int TRY_TIMES = 12;

    private int currentTime = 0;

    private static final int HEARTBEAT_INTERVAL = 5;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("循环触发时间：" + new Date());
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.WRITER_IDLE) {
                if (currentTime <= TRY_TIMES) {
                    System.out.println("currentTime:" + currentTime);
                    currentTime++;
                    HeartBeatRequestPacket heartBeatRequestPacket = new HeartBeatRequestPacket();
                    heartBeatRequestPacket.setMessage("Heartbeat");
                    ctx.channel().writeAndFlush(heartBeatRequestPacket);
                }
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("激活时间是：" + new Date());
        System.out.println("HeartBeatClientHandler channelActive");
        ctx.fireChannelActive();
    }


}
