package protocol.request;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import protocol.Packet;
import protocol.PacketCodeC;
import protocol.response.LoginResponsePacket;
import util.LoginUtil;

import java.util.Date;

import static protocol.commod.Command.LOGIN_REQUEST;


@Data
public class LoginRequestPacket extends Packet {
    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }

    @Override
    public void doChannelRead(ChannelHandlerContext ctx, Packet packet) {

        // 登录流程
        LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(packet.getVersion());
        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            System.out.println(new Date() + ": 登录成功!");
        } else {
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);
            System.out.println(new Date() + ": 登录失败!");
        }
        // 登录响应
        ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
        ctx.channel().writeAndFlush(responseByteBuf);

    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {

        String userName = loginRequestPacket.getUsername();
        String passWord = loginRequestPacket.getPassword();
        return "test".equals(userName) && "test".equals(passWord);
    }
}
