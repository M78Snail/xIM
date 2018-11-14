package protocol.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import protocol.Packet;

import static protocol.commod.Command.MESSAGE_REQUEST;


/**
 * @author duxiaoming
 */
@Data
@NoArgsConstructor
public class MessageRequestPacket extends Packet {

    private String toUserId;
    private String message;

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }


}
