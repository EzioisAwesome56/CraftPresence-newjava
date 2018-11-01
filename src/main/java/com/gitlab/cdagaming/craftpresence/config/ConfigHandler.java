package com.gitlab.cdagaming.craftpresence.config;

import com.gitlab.cdagaming.craftpresence.Constants;
import com.gitlab.cdagaming.craftpresence.handler.StringHandler;
import net.minecraft.client.resources.I18n;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ConfigHandler {
    // Config Names
    // GENERAL
    public String NAME_detectCurseManifest, NAME_detectMultiMCManifest, NAME_detectTechnicPack,
            NAME_showTime, NAME_showCurrentBiome, NAME_showCurrentDimension,
            NAME_showGameState, NAME_clientID, NAME_defaultIcon, NAME_enableJoinRequest;
    // BIOME MESSAGES
    public String NAME_biomeMessages;
    // DIMENSION MESSAGES
    public String NAME_defaultDimensionIcon, NAME_dimensionMessages;
    // SERVER MESSAGES
    public String NAME_defaultServerIcon, NAME_defaultServerName,
            NAME_defaultServerMOTD, NAME_serverMessages;
    // STATUS MESSAGES
    public String NAME_mainmenuMSG, NAME_singleplayerMSG, NAME_loadingMSG,
            NAME_packPlaceholderMSG, NAME_playerPlaceholderMSG, NAME_playerAmountPlaceholderMSG,
            NAME_gameTimePlaceholderMSG, NAME_modsPlaceholderMSG, NAME_vivecraftMessage;
    // ADVANCED
    public String NAME_enableCommands, NAME_enablePERGUI, NAME_enablePERItem, NAME_overwriteServerIcon,
            NAME_splitCharacter, NAME_guiMessages, NAME_itemMessages;

    // Config Variables
    // GENERAL
    public boolean detectCurseManifest, detectMultiMCManifest, detectTechnicPack, showTime,
            showCurrentBiome, showCurrentDimension, showGameState, enableJoinRequest;
    public String clientID, defaultIcon;
    // BIOME MESSAGES
    public String[] biomeMessages;
    // DIMENSION MESSAGES
    public String defaultDimensionIcon;
    public String[] dimensionMessages;
    // SERVER MESSAGES
    public String defaultServerIcon, defaultServerName, defaultServerMOTD;
    public String[] serverMessages;
    // STATUS MESSAGES
    public String mainmenuMSG, singleplayerMSG, loadingMSG, packPlaceholderMSG,
            playerPlaceholderMSG, playerAmountPlaceholderMSG,
            gameTimePlaceholderMSG, modsPlaceholderMSG, vivecraftMessage;
    // ADVANCED
    public boolean enableCommands, enablePERGUI, enablePERItem, overwriteServerIcon;
    public String splitCharacter;
    public String[] guiMessages, itemMessages;
    // CLASS-SPECIFIC - PUBLIC
    public boolean hasChanged = false, hasClientPropertiesChanged = false, rebootOnWorldLoad = false;
    // CLASS-SPECIFIC - PUBLIC
    public Properties properties = new Properties();
    // CLASS-SPECIFIC - PRIVATE
    private String fileName;
    private boolean verified = false, initialized = false, isConfigNew = false;

    public ConfigHandler(String fileName) {
        this.fileName = fileName;
    }

    public void setupInitialValues() {
        // GENERAL
        NAME_detectCurseManifest = I18n.format("gui.config.name.general.detectcursemanifest").replaceAll(" ", "_");
        NAME_detectMultiMCManifest = I18n.format("gui.config.name.general.detectmultimcmanifest").replaceAll(" ", "_");
        NAME_detectTechnicPack = I18n.format("gui.config.name.general.detecttechnicpack").replaceAll(" ", "_");
        NAME_showTime = I18n.format("gui.config.name.general.showtime").replaceAll(" ", "_");
        NAME_showCurrentBiome = I18n.format("gui.config.name.general.showbiome").replaceAll(" ", "_");
        NAME_showCurrentDimension = I18n.format("gui.config.name.general.showdimension").replaceAll(" ", "_");
        NAME_showGameState = I18n.format("gui.config.name.general.showstate").replaceAll(" ", "_");
        NAME_clientID = I18n.format("gui.config.name.general.clientid").replaceAll(" ", "_");
        NAME_defaultIcon = I18n.format("gui.config.name.general.defaulticon").replaceAll(" ", "_");
        NAME_enableJoinRequest = I18n.format("gui.config.name.general.enablejoinrequest").replaceAll(" ", "_");
        detectCurseManifest = true;
        detectMultiMCManifest = true;
        detectTechnicPack = true;
        showTime = true;
        showCurrentBiome = false;
        showCurrentDimension = true;
        showGameState = true;
        clientID = "450485984333660181";
        defaultIcon = "grass";
        enableJoinRequest = true;
        // BIOME MESSAGES
        NAME_biomeMessages = I18n.format("gui.config.name.biomemessages.biomemessages").replaceAll(" ", "_");
        biomeMessages = new String[]{"default;Playing in &biome&"};
        // DIMENSION MESSAGES
        NAME_defaultDimensionIcon = I18n.format("gui.config.name.dimensionmessages.dimensionicon").replaceAll(" ", "_");
        NAME_dimensionMessages = I18n.format("gui.config.name.dimensionmessages.dimensionmessages").replaceAll(" ", "_");
        defaultDimensionIcon = "unknown";
        dimensionMessages = new String[]{"default" + (!StringHandler.isNullOrEmpty(splitCharacter) ? splitCharacter : ";") + "In The &dimension&"};
        // SERVER MESSAGES
        NAME_defaultServerIcon = I18n.format("gui.config.name.servermessages.servericon").replaceAll(" ", "_");
        NAME_defaultServerName = I18n.format("gui.config.name.servermessages.servername").replaceAll(" ", "_");
        NAME_defaultServerMOTD = I18n.format("gui.config.name.servermessages.servermotd").replaceAll(" ", "_");
        NAME_serverMessages = I18n.format("gui.config.name.servermessages.servermessages").replaceAll(" ", "_");
        defaultServerIcon = "default";
        defaultServerName = I18n.format("selectServer.defaultName");
        defaultServerMOTD = I18n.format("craftpresence.defaults.servermessages.servermotd");
        serverMessages = new String[]{"default;Playing on &motd&"};
        // STATUS MESSAGES
        NAME_mainmenuMSG = I18n.format("gui.config.name.statusmessages.mainmenumsg").replaceAll(" ", "_");
        NAME_singleplayerMSG = I18n.format("gui.config.name.statusmessages.singleplayermsg").replaceAll(" ", "_");
        NAME_loadingMSG = I18n.format("gui.config.name.statusmessages.loadingmsg").replaceAll(" ", "_");
        NAME_packPlaceholderMSG = I18n.format("gui.config.name.statusmessages.placeholder.packmsg").replaceAll(" ", "_");
        NAME_playerPlaceholderMSG = I18n.format("gui.config.name.statusmessages.placeholder.playermsg").replaceAll(" ", "_");
        NAME_playerAmountPlaceholderMSG = I18n.format("gui.config.name.statusmessages.placeholder.playeramountmsg").replaceAll(" ", "_");
        NAME_gameTimePlaceholderMSG = I18n.format("gui.config.name.statusmessages.placeholder.gametimemsg").replaceAll(" ", "_");
        NAME_modsPlaceholderMSG = I18n.format("gui.config.name.statusmessages.placeholder.modsmsg").replaceAll(" ", "_");
        NAME_vivecraftMessage = I18n.format("gui.config.name.statusmessages.special.vivecraftmsg").replaceAll(" ", "_");
        mainmenuMSG = I18n.format("craftpresence.defaults.state.mainmenu");
        singleplayerMSG = I18n.format("craftpresence.defaults.state.singleplayer");
        loadingMSG = I18n.format("craftpresence.defaults.state.loading");
        packPlaceholderMSG = I18n.format("craftpresence.defaults.placeholder.pack");
        playerPlaceholderMSG = I18n.format("craftpresence.defaults.placeholder.ign");
        playerAmountPlaceholderMSG = I18n.format("craftpresence.defaults.placeholder.players");
        gameTimePlaceholderMSG = I18n.format("craftpresence.defaults.placeholder.time");
        modsPlaceholderMSG = I18n.format("craftpresence.defaults.placeholder.mods");
        vivecraftMessage = I18n.format("craftpresence.defaults.special.vivecraft");
        // ADVANCED
        NAME_enableCommands = I18n.format("gui.config.name.advanced.enablecommands").replaceAll(" ", "_");
        NAME_enablePERGUI = I18n.format("gui.config.name.advanced.enablepergui").replaceAll(" ", "_");
        NAME_enablePERItem = I18n.format("gui.config.name.advanced.enableperitem").replaceAll(" ", "_");
        NAME_overwriteServerIcon = I18n.format("gui.config.name.advanced.overwriteservericon").replaceAll(" ", "_");
        NAME_splitCharacter = I18n.format("gui.config.name.advanced.splitcharacter").replaceAll(" ", "_");
        NAME_guiMessages = I18n.format("gui.config.name.advanced.guimessages").replaceAll(" ", "_");
        NAME_itemMessages = I18n.format("gui.config.name.advanced.itemmessages").replaceAll(" ", "_");
        enableCommands = true;
        enablePERGUI = false;
        enablePERItem = false;
        overwriteServerIcon = false;
        splitCharacter = ";";
        guiMessages = new String[]{"default;In &gui&"};
        itemMessages = new String[]{"default;Holding &main&"};

        initialized = true;
    }

    public void initialize() {
        try {
            File configFile = new File(fileName);
            File parentDir = configFile.getParentFile();
            isConfigNew = (!parentDir.exists() && parentDir.mkdirs()) || (!configFile.exists() && configFile.createNewFile());
            setupInitialValues();
        } catch (Exception ex) {
            Constants.LOG.error(I18n.format("craftpresence.logger.error.config.save"));
            ex.printStackTrace();
        } finally {
            if (initialized) {
                if (isConfigNew) {
                    updateConfig();
                }
                read();
            }
        }
    }

    public void read() {
        try {
            Reader configReader = new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8);
            properties.load(configReader);
            configReader.close();
        } catch (Exception ex) {
            Constants.LOG.error(I18n.format("craftpresence.logger.error.config.save"));
            ex.printStackTrace();
        } finally {
            try {
                // GENERAL
                detectCurseManifest = StringHandler.isValidBoolean(properties.getProperty(NAME_detectCurseManifest)) ? Boolean.parseBoolean(properties.getProperty(NAME_detectCurseManifest)) : detectCurseManifest;
                detectMultiMCManifest = StringHandler.isValidBoolean(properties.getProperty(NAME_detectMultiMCManifest)) ? Boolean.parseBoolean(properties.getProperty(NAME_detectMultiMCManifest)) : detectMultiMCManifest;
                detectTechnicPack = StringHandler.isValidBoolean(properties.getProperty(NAME_detectTechnicPack)) ? Boolean.parseBoolean(properties.getProperty(NAME_detectTechnicPack)) : detectTechnicPack;
                showTime = StringHandler.isValidBoolean(properties.getProperty(NAME_showTime)) ? Boolean.parseBoolean(properties.getProperty(NAME_showTime)) : showTime;
                showCurrentBiome = StringHandler.isValidBoolean(properties.getProperty(NAME_showCurrentBiome)) ? Boolean.parseBoolean(properties.getProperty(NAME_showCurrentBiome)) : showCurrentBiome;
                showCurrentDimension = StringHandler.isValidBoolean(properties.getProperty(NAME_showCurrentDimension)) ? Boolean.parseBoolean(properties.getProperty(NAME_showCurrentDimension)) : showCurrentDimension;
                showGameState = StringHandler.isValidBoolean(properties.getProperty(NAME_showGameState)) ? Boolean.parseBoolean(properties.getProperty(NAME_showGameState)) : showGameState;
                clientID = !StringHandler.isNullOrEmpty(properties.getProperty(NAME_clientID)) ? properties.getProperty(NAME_clientID) : clientID;
                defaultIcon = !StringHandler.isNullOrEmpty(properties.getProperty(NAME_defaultIcon)) ? properties.getProperty(NAME_defaultIcon) : defaultIcon;
                enableJoinRequest = StringHandler.isValidBoolean(properties.getProperty(NAME_enableJoinRequest)) ? Boolean.parseBoolean(properties.getProperty(NAME_enableJoinRequest)) : enableJoinRequest;
                // BIOME MESSAGES
                biomeMessages = !StringHandler.isNullOrEmpty(properties.getProperty(NAME_biomeMessages).replaceAll("\\[", "").replaceAll("]", "")) ? properties.getProperty(NAME_biomeMessages).replaceAll("\\[", "").replaceAll("]", "").split(", ") : biomeMessages;
                // DIMENSION MESSAGES
                defaultDimensionIcon = !StringHandler.isNullOrEmpty(properties.getProperty(NAME_defaultDimensionIcon)) ? properties.getProperty(NAME_defaultDimensionIcon) : defaultDimensionIcon;
                dimensionMessages = !StringHandler.isNullOrEmpty(properties.getProperty(NAME_dimensionMessages).replaceAll("\\[", "").replaceAll("]", "")) ? properties.getProperty(NAME_dimensionMessages).replaceAll("\\[", "").replaceAll("]", "").split(", ") : dimensionMessages;
                // SERVER MESSAGES
                defaultServerIcon = !StringHandler.isNullOrEmpty(properties.getProperty(NAME_defaultServerIcon)) ? properties.getProperty(NAME_defaultServerIcon) : defaultServerIcon;
                defaultServerName = !StringHandler.isNullOrEmpty(properties.getProperty(NAME_defaultServerName)) ? properties.getProperty(NAME_defaultServerName) : defaultServerName;
                defaultServerMOTD = !StringHandler.isNullOrEmpty(properties.getProperty(NAME_defaultServerMOTD)) ? properties.getProperty(NAME_defaultServerMOTD) : defaultServerMOTD;
                serverMessages = !StringHandler.isNullOrEmpty(properties.getProperty(NAME_serverMessages).replaceAll("\\[", "").replaceAll("]", "")) ? properties.getProperty(NAME_serverMessages).replaceAll("\\[", "").replaceAll("]", "").split(", ") : serverMessages;
                // STATUS MESSAGES
                mainmenuMSG = !StringHandler.isNullOrEmpty(properties.getProperty(NAME_mainmenuMSG)) ? properties.getProperty(NAME_mainmenuMSG) : mainmenuMSG;
                singleplayerMSG = !StringHandler.isNullOrEmpty(properties.getProperty(NAME_singleplayerMSG)) ? properties.getProperty(NAME_singleplayerMSG) : singleplayerMSG;
                loadingMSG = !StringHandler.isNullOrEmpty(properties.getProperty(NAME_loadingMSG)) ? properties.getProperty(NAME_loadingMSG) : loadingMSG;
                packPlaceholderMSG = !StringHandler.isNullOrEmpty(properties.getProperty(NAME_packPlaceholderMSG)) ? properties.getProperty(NAME_packPlaceholderMSG) : packPlaceholderMSG;
                playerPlaceholderMSG = !StringHandler.isNullOrEmpty(properties.getProperty(NAME_playerPlaceholderMSG)) ? properties.getProperty(NAME_playerPlaceholderMSG) : playerPlaceholderMSG;
                playerAmountPlaceholderMSG = !StringHandler.isNullOrEmpty(properties.getProperty(NAME_playerAmountPlaceholderMSG)) ? properties.getProperty(NAME_playerAmountPlaceholderMSG) : playerAmountPlaceholderMSG;
                gameTimePlaceholderMSG = !StringHandler.isNullOrEmpty(properties.getProperty(NAME_gameTimePlaceholderMSG)) ? properties.getProperty(NAME_gameTimePlaceholderMSG) : gameTimePlaceholderMSG;
                modsPlaceholderMSG = !StringHandler.isNullOrEmpty(properties.getProperty(NAME_modsPlaceholderMSG)) ? properties.getProperty(NAME_modsPlaceholderMSG) : modsPlaceholderMSG;
                vivecraftMessage = !StringHandler.isNullOrEmpty(properties.getProperty(NAME_vivecraftMessage)) ? properties.getProperty(NAME_vivecraftMessage) : vivecraftMessage;
                // ADVANCED
                enableCommands = StringHandler.isValidBoolean(properties.getProperty(NAME_enableCommands)) ? Boolean.parseBoolean(properties.getProperty(NAME_enableCommands)) : enableCommands;
                enablePERGUI = StringHandler.isValidBoolean(properties.getProperty(NAME_enablePERGUI)) ? Boolean.parseBoolean(properties.getProperty(NAME_enablePERGUI)) : enablePERGUI;
                enablePERItem = StringHandler.isValidBoolean(properties.getProperty(NAME_enablePERItem)) ? Boolean.parseBoolean(properties.getProperty(NAME_enablePERItem)) : enablePERItem;
                overwriteServerIcon = StringHandler.isValidBoolean(properties.getProperty(NAME_overwriteServerIcon)) ? Boolean.parseBoolean(properties.getProperty(NAME_overwriteServerIcon)) : overwriteServerIcon;
                splitCharacter = !StringHandler.isNullOrEmpty(properties.getProperty(NAME_splitCharacter)) ? properties.getProperty(NAME_splitCharacter) : splitCharacter;
                guiMessages = !StringHandler.isNullOrEmpty(properties.getProperty(NAME_guiMessages).replaceAll("\\[", "").replaceAll("]", "")) ? properties.getProperty(NAME_guiMessages).replaceAll("\\[", "").replaceAll("]", "").split(", ") : guiMessages;
                itemMessages = !StringHandler.isNullOrEmpty(properties.getProperty(NAME_itemMessages).replaceAll("\\[", "").replaceAll("]", "")) ? properties.getProperty(NAME_itemMessages).replaceAll("\\[", "").replaceAll("]", "").split(", ") : itemMessages;
            } catch (NullPointerException ex) {
                verifyConfig();
            } finally {
                if (!verified) {
                    verifyConfig();
                } else {
                    Constants.LOG.info(I18n.format("craftpresence.logger.info.config.save"));
                }
            }
        }
    }

    public void updateConfig() {
        // GENERAL
        properties.setProperty(NAME_detectCurseManifest, Boolean.toString(detectCurseManifest));
        properties.setProperty(NAME_detectMultiMCManifest, Boolean.toString(detectMultiMCManifest));
        properties.setProperty(NAME_detectTechnicPack, Boolean.toString(detectTechnicPack));
        properties.setProperty(NAME_showTime, Boolean.toString(showTime));
        properties.setProperty(NAME_showCurrentBiome, Boolean.toString(showCurrentBiome));
        properties.setProperty(NAME_showCurrentDimension, Boolean.toString(showCurrentDimension));
        properties.setProperty(NAME_showGameState, Boolean.toString(showGameState));
        properties.setProperty(NAME_clientID, clientID);
        properties.setProperty(NAME_defaultIcon, defaultIcon);
        properties.setProperty(NAME_enableJoinRequest, Boolean.toString(enableJoinRequest));
        // BIOME MESSAGES
        properties.setProperty(NAME_biomeMessages, Arrays.toString(biomeMessages));
        // DIMENSION MESSAGES
        properties.setProperty(NAME_defaultDimensionIcon, defaultDimensionIcon);
        properties.setProperty(NAME_dimensionMessages, Arrays.toString(dimensionMessages));
        // SERVER MESSAGES
        properties.setProperty(NAME_defaultServerIcon, defaultServerIcon);
        properties.setProperty(NAME_defaultServerName, defaultServerName);
        properties.setProperty(NAME_defaultServerMOTD, defaultServerMOTD);
        properties.setProperty(NAME_serverMessages, Arrays.toString(serverMessages));
        // STATUS MESSAGES
        properties.setProperty(NAME_mainmenuMSG, mainmenuMSG);
        properties.setProperty(NAME_singleplayerMSG, singleplayerMSG);
        properties.setProperty(NAME_loadingMSG, loadingMSG);
        properties.setProperty(NAME_packPlaceholderMSG, packPlaceholderMSG);
        properties.setProperty(NAME_playerPlaceholderMSG, playerPlaceholderMSG);
        properties.setProperty(NAME_playerAmountPlaceholderMSG, playerAmountPlaceholderMSG);
        properties.setProperty(NAME_gameTimePlaceholderMSG, gameTimePlaceholderMSG);
        properties.setProperty(NAME_modsPlaceholderMSG, modsPlaceholderMSG);
        properties.setProperty(NAME_vivecraftMessage, vivecraftMessage);
        // ADVANCED
        properties.setProperty(NAME_enableCommands, Boolean.toString(enableCommands));
        properties.setProperty(NAME_enablePERGUI, Boolean.toString(enablePERGUI));
        properties.setProperty(NAME_enablePERItem, Boolean.toString(enablePERItem));
        properties.setProperty(NAME_overwriteServerIcon, Boolean.toString(overwriteServerIcon));
        properties.setProperty(NAME_splitCharacter, splitCharacter);
        properties.setProperty(NAME_guiMessages, Arrays.toString(guiMessages));
        properties.setProperty(NAME_itemMessages, Arrays.toString(itemMessages));

        // Check for Conflicts before Saving
        if (showCurrentBiome && showGameState) {
            Constants.LOG.warn(I18n.format("craftpresence.logger.warning.config.conflict.biomestate"));
            showCurrentBiome = false;
            properties.setProperty(NAME_showCurrentBiome, "false");
        }
        if (enablePERGUI && showGameState) {
            Constants.LOG.warn(I18n.format("craftpresence.logger.warning.config.conflict.pergui"));
            enablePERGUI = false;
            properties.setProperty(NAME_enablePERGUI, "false");
        }

        save();
    }

    private void verifyConfig() {
        List<String> validProperties = new ArrayList<>();
        List<String> removedProperties = new ArrayList<>();
        boolean needsFullUpdate = false;

        for (Field field : getClass().getDeclaredFields()) {
            if (field.getName().contains("NAME_")) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(this);
                    validProperties.add(value.toString());
                    if (!properties.stringPropertyNames().contains(value.toString()) && validProperties.contains(value.toString())) {
                        Constants.LOG.error(I18n.format("craftpresence.logger.error.config.emptyprop", value.toString()));
                        needsFullUpdate = true;
                    }
                } catch (Exception ignored) {
                }
            }
        }

        for (String property : properties.stringPropertyNames()) {
            if (!validProperties.contains(property)) {
                Constants.LOG.error(I18n.format("craftpresence.logger.error.config.invalidprop", property));
                removedProperties.add(property);
                properties.remove(property);
                save();
            }
            if (!removedProperties.contains(property)) {
                if (StringHandler.isNullOrEmpty(properties.getProperty(property))) {
                    Constants.LOG.error(I18n.format("craftpresence.logger.error.config.emptyprop", property));
                    needsFullUpdate = true;
                } else {
                    if (property.equals(NAME_clientID) && (properties.getProperty(property).length() != 18 || !StringHandler.isValidLong(properties.getProperty(property)))) {
                        Constants.LOG.error(I18n.format("craftpresence.logger.error.config.invalidprop", property));
                        clientID = "450485984333660181";
                        properties.setProperty(property, clientID);
                        save();
                    }
                    if (property.equals(NAME_splitCharacter) && (properties.getProperty(property).length() != 1 || properties.getProperty(property).matches(".*[a-z].*") || properties.getProperty(property).matches(".*[A-Z].*"))) {
                        Constants.LOG.error(I18n.format("craftpresence.logger.error.config.invalidprop", property));
                        splitCharacter = ";";
                        properties.setProperty(property, splitCharacter);
                        save();
                    }

                    if (property.equals(NAME_biomeMessages) && biomeMessages != null) {
                        boolean defaultFound = !StringHandler.isNullOrEmpty(StringHandler.getConfigPart(biomeMessages, "default", 0, 1, splitCharacter, null));
                        if (!defaultFound) {
                            Constants.LOG.error(I18n.format("craftpresence.logger.error.config.defaultmissing", property));
                            biomeMessages = StringHandler.addToArray(biomeMessages, biomeMessages.length, "default" + splitCharacter + "Playing in &biome&");
                            properties.setProperty(property, Arrays.toString(biomeMessages));
                            save();
                        }
                    }
                    if (property.equals(NAME_dimensionMessages) && dimensionMessages != null) {
                        boolean defaultFound = !StringHandler.isNullOrEmpty(StringHandler.getConfigPart(dimensionMessages, "default", 0, 1, splitCharacter, null));
                        if (!defaultFound) {
                            Constants.LOG.error(I18n.format("craftpresence.logger.error.config.defaultmissing", property));
                            dimensionMessages = StringHandler.addToArray(dimensionMessages, dimensionMessages.length, "default" + splitCharacter + "In The &dimension&");
                            properties.setProperty(property, Arrays.toString(dimensionMessages));
                            save();
                        }
                    }
                    if (property.equals(NAME_serverMessages) && serverMessages != null) {
                        boolean defaultFound = !StringHandler.isNullOrEmpty(StringHandler.getConfigPart(serverMessages, "default", 0, 1, splitCharacter, null));
                        if (!defaultFound) {
                            Constants.LOG.error(I18n.format("craftpresence.logger.error.config.defaultmissing", property));
                            serverMessages = StringHandler.addToArray(serverMessages, serverMessages.length, "default" + splitCharacter + "Playing on &motd&");
                            properties.setProperty(property, Arrays.toString(serverMessages));
                            save();
                        }
                    }
                    if (property.equals(NAME_guiMessages) && guiMessages != null) {
                        boolean defaultFound = !StringHandler.isNullOrEmpty(StringHandler.getConfigPart(guiMessages, "default", 0, 1, splitCharacter, null));
                        if (!defaultFound) {
                            Constants.LOG.error(I18n.format("craftpresence.logger.error.config.defaultmissing", property));
                            guiMessages = StringHandler.addToArray(guiMessages, guiMessages.length, "default" + splitCharacter + "In &gui&");
                            properties.setProperty(property, Arrays.toString(guiMessages));
                            save();
                        }
                    }
                    if (property.equals(NAME_itemMessages) && itemMessages != null) {
                        boolean defaultFound = !StringHandler.isNullOrEmpty(StringHandler.getConfigPart(itemMessages, "default", 0, 1, splitCharacter, null));
                        if (!defaultFound) {
                            Constants.LOG.error(I18n.format("craftpresence.logger.error.config.defaultmissing", property));
                            itemMessages = StringHandler.addToArray(itemMessages, itemMessages.length, "default" + splitCharacter + "Holding &main&");
                            properties.setProperty(property, Arrays.toString(itemMessages));
                            save();
                        }
                    }
                }
            }
        }

        if (needsFullUpdate) {
            setupInitialValues();
            verified = true;
            read();
            updateConfig();
        } else {
            verified = true;
        }
    }

    public void save() {
        try {
            Writer configWriter = new OutputStreamWriter(new FileOutputStream(new File(fileName)), StandardCharsets.UTF_8);
            properties.store(configWriter, null);
            configWriter.close();
        } catch (Exception ex) {
            Constants.LOG.error(I18n.format("craftpresence.logger.error.config.save"));
            ex.printStackTrace();
        }
    }
}