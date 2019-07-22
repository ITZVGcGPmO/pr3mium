package io.github.itzvgcgpmo.pr3mium.handlers;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class ClientConnectedEvent {
    public static final ClientConnectedEvent instance = new ClientConnectedEvent();
    public static String host;

    @SubscribeEvent
    public void onClientConnectedToServerEvent(FMLNetworkEvent.ClientConnectedToServerEvent event)
    {
        RegOrLoginChatEvent.instance.canLogin = true;
        RegOrLoginChatEvent.instance.canRegister = true;
        host = event.getManager().getRemoteAddress().toString().split("/")[0];
        MinecraftForge.EVENT_BUS.register(RegOrLoginChatEvent.instance);
        MinecraftForge.EVENT_BUS.register(RegOrLoginTickCount.instance);
    }
}
