package io.github.itzvgcgpmo.pr3mium.handlers;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static io.github.itzvgcgpmo.pr3mium.Pr3mium.*;

public class RegOrLoginTickCount {
    public static final RegOrLoginTickCount instance = new RegOrLoginTickCount();

    public int t_counter;
    @SubscribeEvent
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event)
    {
        if(this.t_counter < wait_t) {
            this.t_counter++;
        } else {
            this.t_counter = 0;
            if (skin_url.length() > 0) { // TODO: check if skin already applied on server. try to compare playerskin & image, rather than historical lookup
//                logger.info("executing /skin command");
                Minecraft.getMinecraft().player.sendChatMessage("/skin " + skin_url);
            }
            MinecraftForge.EVENT_BUS.unregister(RegOrLoginChatEvent.instance);
            MinecraftForge.EVENT_BUS.unregister(RegOrLoginTickCount.instance);
        }
    }
}
