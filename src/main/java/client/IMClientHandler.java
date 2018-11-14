package client;

import client.console.GroupMessageResponseHandler;
import client.handler.*;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.Packet;


import java.util.HashMap;
import java.util.Map;

import static protocol.commod.Command.*;


public class IMClientHandler extends SimpleChannelInboundHandler<Packet> {


    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    public IMClientHandler() {
        handlerMap = new HashMap<>();

        handlerMap.put(MESSAGE_RESPONSE, MessageResponseHandler.INSTANCE);
        handlerMap.put(CREATE_GROUP_RESPONSE, CreateGroupResponseHandler.INSTANCE);
        handlerMap.put(JOIN_GROUP_RESPONSE, JoinGroupResponseHandler.INSTANCE);
        handlerMap.put(QUIT_GROUP_RESPONSE, QuitGroupResponseHandler.INSTANCE);
        handlerMap.put(LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponseHandler.INSTANCE);
        handlerMap.put(GROUP_MESSAGE_RESPONSE, GroupMessageResponseHandler.INSTANCE);
//        handlerMap.put(LOGOUT_REQUEST, LogoutRequestHandler.INSTANCE);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        handlerMap.get(packet.getCommand()).channelRead(ctx, packet);
    }
}
