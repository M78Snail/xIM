package client.console;

import io.netty.channel.Channel;
import protocol.request.MessageRequestPacket;

import java.util.Scanner;

public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner sc, Channel channel) {
        String toUserId = sc.next();
        String message = sc.next();
        channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
    }
}
