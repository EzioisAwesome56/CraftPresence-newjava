package com.gitlab.cdagaming.craftpresence.handler;

import com.gitlab.cdagaming.craftpresence.Constants;
import com.gitlab.cdagaming.craftpresence.CraftPresence;
import com.gitlab.cdagaming.craftpresence.handler.commands.CPCommands;
import com.gitlab.cdagaming.craftpresence.handler.curse.ManifestHandler;
import com.gitlab.cdagaming.craftpresence.handler.discord.assets.DiscordAsset;
import com.gitlab.cdagaming.craftpresence.handler.discord.assets.DiscordAssetHandler;
import com.gitlab.cdagaming.craftpresence.handler.multimc.InstanceHandler;
import com.gitlab.cdagaming.craftpresence.handler.technic.PackHandler;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.LoaderState;

public class CommandHandler {
    private static void registerCommands() {
        ClientCommandHandler.instance.registerCommand(new CPCommands());
    }

    public static void reloadData() {
        if (CraftPresence.CONFIG.showCurrentBiome && !CraftPresence.CONFIG.showGameState) {
            CraftPresence.BIOMES.getBiomes();
        } else {
            CraftPresence.BIOMES.emptyData();
        }

        if (CraftPresence.CONFIG.showCurrentDimension) {
            CraftPresence.DIMENSIONS.getDimensions();
        } else {
            CraftPresence.DIMENSIONS.emptyData();
        }

        if (CraftPresence.CONFIG.enablePERItem) {
            CraftPresence.ENTITIES.getEntities();
        } else {
            CraftPresence.ENTITIES.emptyData();
        }

        if (CraftPresence.CONFIG.showGameState) {
            CraftPresence.SERVER.getServerAddresses();
        } else {
            CraftPresence.SERVER.emptyData();
        }

        if (CraftPresence.CONFIG.enablePERGUI && !CraftPresence.CONFIG.showGameState) {
            CraftPresence.GUIS.getGUIs();
        } else {
            CraftPresence.GUIS.emptyData();
        }
    }

    public static void rebootRPC() {
        CraftPresence.CLIENT.shutDown();
        CraftPresence.CLIENT.init();
        reloadData();
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

        if (!CraftPresence.CONFIG.hasChanged) {
            if (CraftPresence.CONFIG.showGameState) {
                CraftPresence.CLIENT.DETAILS = CraftPresence.CONFIG.mainmenuMSG;
            } else {
                CraftPresence.CLIENT.DETAILS = "";
            }
        }
        CraftPresence.CLIENT.setImage(CraftPresence.CONFIG.defaultIcon, DiscordAsset.AssetType.LARGE);
        CraftPresence.CLIENT.LARGEIMAGETEXT = I18n.format("craftpresence.defaults.state.mcversion", Constants.MCVersion);
        CraftPresence.CLIENT.updatePresence(CraftPresence.CLIENT.buildRichPresence());
    }

    public static void setLoadingPresence(final LoaderState.ModState state) {
        CraftPresence.CLIENT.SMALLIMAGEKEY = "";
        CraftPresence.CLIENT.SMALLIMAGETEXT = "";

        CraftPresence.CLIENT.setImage(CraftPresence.CONFIG.defaultIcon, DiscordAsset.AssetType.LARGE);
        if (!CraftPresence.CONFIG.hasChanged) {
            if (CraftPresence.CONFIG.showGameState) {
                CraftPresence.CLIENT.DETAILS = CraftPresence.CONFIG.loadingMSG;
                CraftPresence.CLIENT.GAME_STATE = I18n.format("craftpresence.defaults.state.loading.status", state);
                CraftPresence.CLIENT.LARGEIMAGETEXT = CraftPresence.CLIENT.GAME_STATE;
            } else {
                CraftPresence.CLIENT.DETAILS = "";
                CraftPresence.CLIENT.GAME_STATE = "";
                CraftPresence.CLIENT.LARGEIMAGETEXT = I18n.format("craftpresence.defaults.state.mcversion", Constants.MCVersion);
            }
        }
        CraftPresence.CLIENT.updatePresence(CraftPresence.CLIENT.buildRichPresence());
    }
}
