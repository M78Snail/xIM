package protocol.response;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import protocol.Packet;
import protocol.PacketCodeC;
import protocol.request.MessageRequestPacket;

import java.util.Date;

import static protocol.commod.Command.MESSAGE_RESPONSE;

/**
 * @author duxiaoming
 */
@Data
public class MessageResponsePacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }

    @Override
    public void doChannelRead(ChannelHandlerContext ctx, Packet packet) {



        MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
        System.out.println(new Date() + ": 收到服务端的消息: " + messageResponsePacket.getMessage());

    }
}
