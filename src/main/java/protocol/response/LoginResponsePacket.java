package protocol.response;

import lombok.Data;
import protocol.Packet;


import static protocol.commod.Command.LOGIN_RESPONSE;


/**
 * @author duxiaoming
 */

@Data
public class LoginResponsePacket extends Packet {
    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }






}
