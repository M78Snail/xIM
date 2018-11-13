package protocol.response;

import lombok.Data;
import protocol.Packet;


import static protocol.commod.Command.MESSAGE_RESPONSE;

/**
 * @author duxiaoming
 */
@Data
public class MessageResponsePacket extends Packet {

    private String fromUserId;

    private String fromUserName;

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }


}
