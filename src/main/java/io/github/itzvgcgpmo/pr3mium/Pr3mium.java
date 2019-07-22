package io.github.itzvgcgpmo.pr3mium;

import io.github.itzvgcgpmo.pr3mium.handlers.ClientConnectedEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = Pr3mium.MODID, name = Pr3mium.NAME, version = Pr3mium.VERSION, canBeDeactivated=true)
public class Pr3mium {
    public static final String MODID = "Pr3mium";
    public static final String NAME = "Pr3mium";
    public static final String VERSION = "1.0";
    public static Logger logger;

    public static String password;
    public static String skin_url;
    public static int pw_len;
    public static int wait_t;
    public static String logcmd_alias;
    public static String regcmd_alias;
    public static String pw_alias;
    public static String regex_lgin;
    public static String regex_reg1;
    public static String regex_reg2;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        password = config.getString("Password", "Module", "", "Your master password (see: https://ss64.com/pass )");
        skin_url = config.getString("Skin URL", "Module", "", "A URL for your desired skin");
        pw_len = config.getInt("GenPW Length", "Module", 20, 4, 40, "The length of generated passwords");
        wait_t = config.getInt("LoginAfter Ticks", "Module", 50, 20, 999, "Ticks to wait, then considered already logged in");
        logcmd_alias = config.getString("/login aliases", "Module", "login|log|l", "/login command aliases");
        regcmd_alias = config.getString("/register aliases", "Module", "register|reg|r", "/register command aliases");
        pw_alias = config.getString("password aliases", "Module", "password|ConfirmPassword|pass", "password variable aliases");
        regex_lgin = ".*\\/?("+logcmd_alias+")\\s*[\\[<(]?("+pw_alias+")[\\]>)]?.*";
        regex_reg1 = ".*\\/?("+regcmd_alias+")\\s*[\\[<(]?("+pw_alias+")[\\]>)]?.*";
        regex_reg2 = ".*\\/?("+regcmd_alias+")\\s*[\\[<(]?("+pw_alias+")[\\]>)]?\\s*[\\[<(]?("+pw_alias+")[\\]>)]?.*";
        config.save();
        logger = event.getModLog();
        MinecraftForge.EVENT_BUS.register(ClientConnectedEvent.instance);
    }

    public static void plMsg(String message) {
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString(""+TextFormatting.LIGHT_PURPLE+TextFormatting.BOLD+"[Pr3mium] "+message));
    }
}
