package io.github.itzvgcgpmo.pr3mium.handlers;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.binary.Base64;

import static io.github.itzvgcgpmo.pr3mium.Pr3mium.*;


public class RegOrLoginChatEvent {
    public static final RegOrLoginChatEvent instance = new RegOrLoginChatEvent();
    public boolean canRegister;
    public boolean canLogin;
    public boolean hidemsg = false;
    private String uniquepwd;

    @SubscribeEvent
    public void onEntity(ClientChatReceivedEvent event)
    {
        if (password.length() > 0){
            if (uniquepwd==null) { // https://github.com/salsifis/ss64-password-generators https://ss64.com/pass
                logger.info("Reminder: <masterpwd> + "+ClientConnectedEvent.host+" -> <serverpwd> (https://ss64.com/pass algo)");
                uniquepwd = Base64.encodeBase64String(DigestUtils.sha256(password+":"+ClientConnectedEvent.host)).replace("=", "");
                uniquepwd = uniquepwd.replace("+", "E").replace("/", "a");
                if (uniquepwd.length() > pw_len)
                    uniquepwd = uniquepwd.substring(0,pw_len);
            }
            String msg = event.getMessage().getUnformattedText();
            if (canLogin && msg.matches(regex_lgin)) {
                Minecraft.getMinecraft().player.sendChatMessage("/login "+uniquepwd);
                canLogin = false;
            } else if (canRegister && msg.matches(regex_reg2)) {
                Minecraft.getMinecraft().player.sendChatMessage("/register "+uniquepwd+" "+uniquepwd);
            } else if (canRegister && msg.matches(regex_reg1)) {
                Minecraft.getMinecraft().player.sendChatMessage("/register "+uniquepwd);
            } else { // if no command completed
                if (msg.startsWith("[SkinsRestorer]") && hidemsg) // hide `[SkinsRestorer]`
                    event.setCanceled(true);
                return;
            } // if login or register command completed
            canRegister = false;
            if (hidemsg) // hide messages that match `/login` or `/register`
                event.setCanceled(true);
        } else {
            plMsg("add password in mod config and relog");
            MinecraftForge.EVENT_BUS.unregister(RegOrLoginChatEvent.instance);
            MinecraftForge.EVENT_BUS.unregister(RegOrLoginTickCount.instance);
        }
    }
}
