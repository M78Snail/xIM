package protocol.request;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import protocol.Packet;
import protocol.PacketCodeC;
import protocol.response.MessageResponsePacket;

import java.util.Date;

import static protocol.commod.Command.MESSAGE_REQUEST;


/**
 * @author duxiaoming
 */
@Data
public class MessageRequestPacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }

    @Override
    public void doChannelRead(ChannelHandlerContext ctx, Packet packet) {
        // 客户端发来消息
        MessageRequestPacket messageRequestPacket = ((MessageRequestPacket) packet);

        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());
        messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
        ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
        ctx.channel().writeAndFlush(responseByteBuf);
    }
}
