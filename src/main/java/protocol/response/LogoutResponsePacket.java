package protocol.response;

import protocol.Packet;

import static protocol.commod.Command.LOGOUT_RESPONSE;

public class LogoutResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return LOGOUT_RESPONSE;
    }
}
