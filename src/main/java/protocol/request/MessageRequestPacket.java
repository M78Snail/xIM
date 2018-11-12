package protocol.request;

import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import protocol.Packet;

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


}
