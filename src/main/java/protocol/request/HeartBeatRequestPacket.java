package protocol.request;

import lombok.Data;
import protocol.Packet;

import static protocol.commod.Command.HEARTBEAT_REQUEST;


@Data
public class HeartBeatRequestPacket extends Packet {

    private String message;
    @Override
    public Byte getCommand() {
        return HEARTBEAT_REQUEST;
    }
}
