package com.gitlab.cdagaming.craftpresence.handler;

import com.gitlab.cdagaming.craftpresence.Constants;
import com.gitlab.cdagaming.craftpresence.CraftPresence;
import com.gitlab.cdagaming.craftpresence.handler.commands.CPCommands;
import com.gitlab.cdagaming.craftpresence.handler.curse.ManifestHandler;
import com.gitlab.cdagaming.craftpresence.handler.discord.assets.DiscordAsset;
import com.gitlab.cdagaming.craftpresence.handler.discord.assets.DiscordAssetHandler;
import com.gitlab.cdagaming.craftpresence.handler.multimc.InstanceHandler;
import com.gitlab.cdagaming.craftpresence.handler.technic.PackHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.LoaderState;

public class CommandHandler {
    private static void registerCommands() {
        ClientCommandHandler.instance.registerCommand(new CPCommands());
    }

    public static void reloadData() {
        CraftPresence.instance = Minecraft.getMinecraft();
        CraftPresence.player = CraftPresence.instance.player;

        CraftPresence.BIOMES.onTick();
        CraftPresence.DIMENSIONS.onTick();
        CraftPresence.GUIS.onTick();
        CraftPresence.ENTITIES.onTick();
        CraftPresence.SERVER.onTick();
    }

    public static void rebootRPC() {
        CraftPresence.CLIENT.shutDown();
        if (!CraftPresence.CLIENT.CLIENT_ID.equals(CraftPresence.CONFIG.clientID)) {
            DiscordAssetHandler.emptyData();
            CraftPresence.CLIENT.CLIENT_ID = CraftPresence.CONFIG.clientID;
        }
        DiscordAssetHandler.loadAssets();
        CraftPresence.CLIENT.init();
        CraftPresence.CLIENT.updatePresence(CraftPresence.CLIENT.buildRichPresence());
    }

    public static void init() {
        if (CraftPresence.CONFIG.enableCommands) {
            registerCommands();
        }
        if (CraftPresence.CONFIG.detectCurseManifest && !CraftPresence.packFound) {
            ManifestHandler.loadManifest();
        }
        if (CraftPresence.CONFIG.detectMultiMCManifest && !CraftPresence.packFound) {
            InstanceHandler.loadInstance();
        }
        if (CraftPresence.CONFIG.detectTechnicPack && !CraftPresence.packFound) {
            PackHandler.loadPack();
        }
        DiscordAssetHandler.loadAssets();
    }

    public static void setMainMenuPresence() {
        CraftPresence.CLIENT.SMALLIMAGEKEY = "";
        CraftPresence.CLIENT.SMALLIMAGETEXT = "";
        CraftPresence.CLIENT.GAME_STATE = "";
        CraftPresence.CLIENT.PARTY_ID = "";
        CraftPresence.CLIENT.PARTY_SIZE = 0;
        CraftPresence.CLIENT.PARTY_MAX = 0;
        CraftPresence.CLIENT.JOIN_SECRET = "";
        CraftPresence.CLIENT.DETAILS = CraftPresence.SERVER.enabled ? CraftPresence.CONFIG.mainmenuMSG : "";
        CraftPresence.CLIENT.setImage(CraftPresence.CONFIG.defaultIcon, DiscordAsset.AssetType.LARGE);
        CraftPresence.CLIENT.LARGEIMAGETEXT = I18n.format("craftpresence.defaults.state.mcversion", Constants.MCVersion);
        CraftPresence.CLIENT.updatePresence(CraftPresence.CLIENT.buildRichPresence());
    }

    public static Boolean isOnMainMenuPresence() {
        return !CraftPresence.CONFIG.hasChanged &&
                StringHandler.isNullOrEmpty(CraftPresence.CLIENT.SMALLIMAGEKEY) &&
                StringHandler.isNullOrEmpty(CraftPresence.CLIENT.SMALLIMAGETEXT) &&
                StringHandler.isNullOrEmpty(CraftPresence.CLIENT.GAME_STATE) &&
                StringHandler.isNullOrEmpty(CraftPresence.CLIENT.PARTY_ID) &&
                CraftPresence.CLIENT.PARTY_SIZE == 0 &&
                CraftPresence.CLIENT.PARTY_MAX == 0 &&
                StringHandler.isNullOrEmpty(CraftPresence.CLIENT.JOIN_SECRET) &&
                (!StringHandler.isNullOrEmpty(CraftPresence.CLIENT.DETAILS) &&
                        CraftPresence.CLIENT.DETAILS.equals(CraftPresence.SERVER.enabled ? CraftPresence.CONFIG.mainmenuMSG : "")
                ) &&
                (!StringHandler.isNullOrEmpty(CraftPresence.CLIENT.LARGEIMAGEKEY) &&
                        CraftPresence.CLIENT.LARGEIMAGEKEY.equals(CraftPresence.CONFIG.defaultIcon)
                ) &&
                (!StringHandler.isNullOrEmpty(CraftPresence.CLIENT.LARGEIMAGETEXT) &&
                        CraftPresence.CLIENT.LARGEIMAGETEXT.equals(I18n.format("craftpresence.defaults.state.mcversion", Constants.MCVersion))
                );
    }

    public static void setLoadingPresence(final LoaderState.ModState state) {
        CraftPresence.CLIENT.SMALLIMAGEKEY = "";
        CraftPresence.CLIENT.SMALLIMAGETEXT = "";
        CraftPresence.CLIENT.DETAILS = CraftPresence.SERVER.enabled ? CraftPresence.CONFIG.loadingMSG : "";
        CraftPresence.CLIENT.GAME_STATE = CraftPresence.SERVER.enabled ? I18n.format("craftpresence.defaults.state.loading.status", state) : "";
        CraftPresence.CLIENT.setImage(CraftPresence.CONFIG.defaultIcon, DiscordAsset.AssetType.LARGE);
        CraftPresence.CLIENT.LARGEIMAGETEXT = CraftPresence.SERVER.enabled ? CraftPresence.CLIENT.GAME_STATE : I18n.format("craftpresence.defaults.state.mcversion", Constants.MCVersion);
        CraftPresence.CLIENT.updatePresence(CraftPresence.CLIENT.buildRichPresence());
    }
}
