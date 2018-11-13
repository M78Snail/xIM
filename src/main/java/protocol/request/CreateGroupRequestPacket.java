package protocol.request;

import lombok.Data;
import protocol.Packet;

import java.util.List;

import static protocol.commod.Command.CREATE_GROUP_REQUEST;

@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;

    @Override
    public Byte getCommand() {

        return CREATE_GROUP_REQUEST;
    }
}
