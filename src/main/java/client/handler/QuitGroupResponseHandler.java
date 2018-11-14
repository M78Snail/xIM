package client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.response.QuitGroupResponsePacket;

@ChannelHandler.Sharable
public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {

    /**
     * 构造单例
     */
    public static final QuitGroupResponseHandler INSTANCE = new QuitGroupResponseHandler();

    private QuitGroupResponseHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket responsePacket) throws Exception {
        if (responsePacket.isSuccess()) {
            System.out.println("退出群聊[" + responsePacket.getGroupId() + "]成功！");

        } else {
            System.out.println("退出群聊[" + responsePacket.getGroupId() + "]失败！");

        }
    }
}
