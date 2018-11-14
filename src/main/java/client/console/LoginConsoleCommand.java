package client.console;

import io.netty.channel.Channel;
import protocol.request.LoginRequestPacket;

import java.util.Scanner;

public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner sc, Channel channel) {
        System.out.print("输入用户名登录: ");
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        String username = sc.nextLine();

        loginRequestPacket.setUsername(username);

        // 密码使用默认的
        loginRequestPacket.setPassword("pwd");

        // 发送登录数据包
        channel.writeAndFlush(loginRequestPacket);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
