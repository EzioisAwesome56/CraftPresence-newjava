/*
 * MIT License
 *
 * Copyright (c) 2018 - 2020 CDAGaming (cstack2011@yahoo.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.gitlab.cdagaming.craftpresence.utils.gui.controls;

import com.gitlab.cdagaming.craftpresence.CraftPresence;
import com.gitlab.cdagaming.craftpresence.utils.StringUtils;
import com.gitlab.cdagaming.craftpresence.utils.gui.GuiUtils;
import com.google.common.collect.Lists;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;

import java.util.List;

/**
 * An Extended and Globalized Gui Screen
 *
 * @author CDAGaming
 */
public class ExtendedScreen extends Screen {
    /**
     * The Parent or Past Screen
     */
    public final Screen parentScreen;
    /**
     * The Current Screen Instance
     */
    public final Screen currentScreen;
    /**
     * Similar to buttonList, a list of compatible controls in this Screen
     */
    private final List<Element> extendedControls = Lists.newArrayList();
    /**
     * Similar to buttonList, a list of compatible ScrollLists in this Screen
     */
    private final List<ScrollableListControl> extendedLists = Lists.newArrayList();
    /**
     * Variable Needed to ensure all buttons are initialized before rendering to prevent an NPE
     */
    private boolean initialized = false;

    /**
     * The Last Ticked Mouse X Coordinate
     */
    private int lastMouseX = 0;
    /**
     * The Last Ticked Mouse Y Coordinate
     */
    private int lastMouseY = 0;

    /**
     * Initialization Event for this Control, assigning defined arguments
     *
     * @param parentScreen The Parent Screen for this Instance
     */
    public ExtendedScreen(Screen parentScreen) {
        super(new LiteralText(""));
        minecraft = CraftPresence.instance;
        currentScreen = this;
        this.parentScreen = parentScreen;
    }

    /**
     * Pre-Initializes this Screen
     * <p>
     * Responsible for Setting preliminary data
     */
    @Override
    public void init() {
        // Clear Data before Initialization
        buttons.clear();
        extendedControls.clear();
        extendedLists.clear();

        minecraft.keyboard.enableRepeatEvents(true);
        initializeUi();
        super.init();
        initialized = true;
    }

    /**
     * Initializes this Screen
     * <p>
     * Responsible for setting initial Data and creating controls
     */
    public void initializeUi() {
        // N/A
    }

    /**
     * Event to trigger upon Window Resize
     *
     * @param mcIn The Minecraft Instance
     * @param w    The New Screen Width
     * @param h    The New Screen Height
     */
    @Override
    public void resize(MinecraftClient mcIn, int w, int h) {
        initialized = false;
        super.resize(mcIn, w, h);
    }

    /**
     * Adds a Compatible Button to this Screen with specified type
     *
     * @param buttonIn The Button to add to this Screen
     * @param <T>      The Button's Class Type
     * @return The added button with attached class type
     */
    @Override
    protected <T extends AbstractButtonWidget> T addButton(T buttonIn) {
        return addControl(buttonIn);
    }

    /**
     * Adds a Compatible Control to this Screen with specified type
     *
     * @param buttonIn The Control to add to this Screen
     * @param <T>      The Control's Class Type
     * @return The added control with attached class type
     */
    protected <T extends Element> T addControl(T buttonIn) {
        if (buttonIn instanceof ButtonWidget && !buttons.contains(buttonIn)) {
            buttons.add((ButtonWidget) buttonIn);
        }
        if (!extendedControls.contains(buttonIn)) {
            extendedControls.add(buttonIn);
        }

        return buttonIn;
    }

    /**
     * Adds a Compatible Scroll List to this Screen with specified type
     *
     * @param buttonIn The Scroll List to add to this Screen
     * @param <T>      The Scroll List's Class Type
     * @return The added scroll list with attached class type
     */
    protected <T extends ScrollableListControl> T addList(T buttonIn) {
        if (!extendedLists.contains(buttonIn)) {
            extendedLists.add(buttonIn);
        }

        return buttonIn;
    }

    /**
     * Pre-Preliminary Render Event, executes before preRender
     * <p>
     * Primarily used for rendering critical elements before other elements
     */
    public void renderCriticalData() {
        CraftPresence.GUIS.drawBackground(width, height);
    }

    /**
     * Preliminary Render Event, executes after renderCriticalData and before postRender
     * <p>
     * Primarily used for rendering title data and preliminary elements
     */
    public void preRender() {
        // N/A
    }

    /**
     * Post-Render event, executes after super event and preRender
     * <p>
     * Primarily used for rendering hover data
     */
    public void postRender() {
        // N/A
    }

    /**
     * Renders this Screen, including controls and post-Hover Events
     *
     * @param mouseX       The Event Mouse X Coordinate
     * @param mouseY       The Event Mouse Y Coordinate
     * @param partialTicks The Rendering Tick Rate
     */
    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        // Ensures initialization events have run first, preventing an NPE
        if (initialized) {
            renderCriticalData();
            preRender();

            for (ScrollableListControl listControl : extendedLists) {
                listControl.render(mouseX, mouseY, partialTicks);
            }

            for (Element extendedControl : extendedControls) {
                if (extendedControl instanceof ExtendedButtonControl) {
                    final ExtendedButtonControl button = (ExtendedButtonControl) extendedControl;
                    button.render(mouseX, mouseY, partialTicks);
                }
                if (extendedControl instanceof ExtendedTextControl) {
                    final ExtendedTextControl textField = (ExtendedTextControl) extendedControl;
                    textField.render(mouseX, mouseY, partialTicks);
                }
            }

            super.render(mouseX, mouseY, partialTicks);

            lastMouseX = mouseX;
            lastMouseY = mouseY;

            for (Element extendedControl : extendedControls) {
                if (extendedControl instanceof ExtendedButtonControl) {
                    final ExtendedButtonControl extendedButton = (ExtendedButtonControl) extendedControl;
                    if (CraftPresence.GUIS.isMouseOver(mouseX, mouseY, extendedButton)) {
                        extendedButton.onHover();
                    }
                }
            }

            postRender();
        }
    }

    /**
     * Event to trigger upon Pressing a Key
     *
     * @param keyCode The KeyCode entered, if any
     * @param mouseX  The Event Mouse X Coordinate
     * @param mouseY  The Event Mouse Y Coordinate
     * @return The Event Result
     */
    @Override
    public boolean keyPressed(int keyCode, int mouseX, int mouseY) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            CraftPresence.GUIS.openScreen(parentScreen);
        }

        for (Element extendedControl : extendedControls) {
            if (extendedControl instanceof ExtendedButtonControl) {
                final ExtendedButtonControl button = (ExtendedButtonControl) extendedControl;
                button.keyPressed(keyCode, mouseX, mouseY);
            }
            if (extendedControl instanceof ExtendedTextControl) {
                final ExtendedTextControl textField = (ExtendedTextControl) extendedControl;
                textField.keyPressed(keyCode, mouseX, mouseY);
            }
        }
        return super.keyPressed(keyCode, mouseX, mouseY);
    }

    /**
     * Event to trigger upon Typing a Character
     *
     * @param typedChar The typed Character, if any
     * @param keyCode   The KeyCode entered, if any
     * @return The Event Result
     */
    @Override
    public boolean charTyped(char typedChar, int keyCode) {
        for (ScrollableListControl listControl : extendedLists) {
            listControl.charTyped(typedChar, keyCode);
        }

        for (Element extendedControl : extendedControls) {
            if (extendedControl instanceof ExtendedButtonControl) {
                final ExtendedButtonControl button = (ExtendedButtonControl) extendedControl;
                button.charTyped(typedChar, keyCode);
            }
            if (extendedControl instanceof ExtendedTextControl) {
                final ExtendedTextControl textField = (ExtendedTextControl) extendedControl;
                textField.charTyped(typedChar, keyCode);
            }
        }
        return super.charTyped(typedChar, keyCode);
    }

    /**
     * Event to trigger upon the mouse being clicked
     *
     * @param mouseX      The Event Mouse X Coordinate
     * @param mouseY      The Event Mouse Y Coordinate
     * @param mouseButton The Event Mouse Button Clicked
     * @return The Event Result
     */
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        for (ScrollableListControl listControl : extendedLists) {
            listControl.mouseClicked(mouseX, mouseY, mouseButton);
        }

        for (Element extendedControl : extendedControls) {
            if (extendedControl instanceof ExtendedButtonControl) {
                final ExtendedButtonControl button = (ExtendedButtonControl) extendedControl;
                button.mouseClicked(mouseX, mouseY, mouseButton);
            }
            if (extendedControl instanceof ExtendedTextControl) {
                final ExtendedTextControl textField = (ExtendedTextControl) extendedControl;
                textField.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Event to trigger upon scrolling the mouse
     *
     * @param mouseX       The Event Mouse X Coordinate
     * @param mouseY       The Event Mouse Y Coordinate
     * @param scrollAmount The Scroll Amount
     * @return The Event Result
     */
    @Override
    public boolean mouseScrolled(final double mouseX, final double mouseY, final double scrollAmount) {
        for (ScrollableListControl listControl : extendedLists) {
            listControl.mouseScrolled(mouseX, mouseY, scrollAmount);
        }

        for (Element extendedControl : extendedControls) {
            if (extendedControl instanceof ExtendedButtonControl) {
                final ExtendedButtonControl button = (ExtendedButtonControl) extendedControl;
                button.mouseScrolled(mouseX, mouseY, scrollAmount);
            }
            if (extendedControl instanceof ExtendedTextControl) {
                final ExtendedTextControl textField = (ExtendedTextControl) extendedControl;
                textField.mouseScrolled(mouseX, mouseY, scrollAmount);
            }
        }
        return super.mouseScrolled(mouseX, mouseY, scrollAmount);
    }

    /**
     * Event to Trigger upon Dragging the Mouse
     *
     * @param mouseX      The Event Mouse X Coordinate
     * @param mouseY      The Event Mouse Y Coordinate
     * @param mouseButton The Event Mouse Button Clicked
     * @param scrollX     The Scroll Amount for the Mouse X Coordinate
     * @param scrollY     The Scroll Amount for the Mouse Y Coordinate
     * @return The Event Result
     */
    @Override
    public boolean mouseDragged(final double mouseX, final double mouseY, final int mouseButton, final double scrollX, final double scrollY) {
        for (ScrollableListControl listControl : extendedLists) {
            listControl.mouseDragged(mouseX, mouseY, mouseButton, scrollX, scrollY);
        }

        for (Element extendedControl : extendedControls) {
            if (extendedControl instanceof ExtendedButtonControl) {
                final ExtendedButtonControl button = (ExtendedButtonControl) extendedControl;
                button.mouseDragged(mouseX, mouseY, mouseButton, scrollX, scrollY);
            }
            if (extendedControl instanceof ExtendedTextControl) {
                final ExtendedTextControl textField = (ExtendedTextControl) extendedControl;
                textField.mouseDragged(mouseX, mouseY, mouseButton, scrollX, scrollY);
            }
        }
        return super.mouseDragged(mouseX, mouseY, mouseButton, scrollX, scrollY);
    }

    /**
     * Event to Trigger upon Releasing the Mouse
     *
     * @param mouseX      The Event Mouse X Coordinate
     * @param mouseY      The Event Mouse Y Coordinate
     * @param mouseButton The Event Mouse Button Clicked
     * @return The Event Result
     */
    @Override
    public boolean mouseReleased(final double mouseX, final double mouseY, final int mouseButton) {
        for (ScrollableListControl listControl : extendedLists) {
            listControl.mouseReleased(mouseX, mouseY, mouseButton);
        }

        for (Element extendedControl : extendedControls) {
            if (extendedControl instanceof ExtendedButtonControl) {
                final ExtendedButtonControl button = (ExtendedButtonControl) extendedControl;
                button.mouseReleased(mouseX, mouseY, mouseButton);
            }
            if (extendedControl instanceof ExtendedTextControl) {
                final ExtendedTextControl textField = (ExtendedTextControl) extendedControl;
                textField.mouseReleased(mouseX, mouseY, mouseButton);
            }
        }
        return super.mouseReleased(mouseX, mouseY, mouseButton);
    }

    /**
     * Event to trigger on each tick
     */
    @Override
    public void tick() {
        for (Element extendedControl : extendedControls) {
            if (extendedControl instanceof ExtendedTextControl) {
                final ExtendedTextControl textField = (ExtendedTextControl) extendedControl;
                textField.tick();
            }
        }
    }

    /**
     * Decide whether the Screen can close with Vanilla Methods
     *
     * @return whether the Screen can close with Vanilla Methods
     */
    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    /**
     * Event to trigger upon exiting the Gui
     */
    @Override
    public void onClose() {
        initialized = false;
        CraftPresence.GUIS.resetIndex();
        minecraft.keyboard.enableRepeatEvents(false);
    }

    /**
     * Renders a String in the Screen, in the style of a notice
     *
     * @param notice The List of Strings to render
     */
    public void renderNotice(final List<String> notice) {
        renderNotice(notice, 2, 3, false, false);
    }

    /**
     * Renders a String in the Screen, in the style of a notice
     *
     * @param notice      The List of Strings to render
     * @param widthScale  The Scale/Value away from the center X to render at
     * @param heightScale The Scale/Value away from the center Y to render at
     */
    public void renderNotice(final List<String> notice, float widthScale, float heightScale) {
        renderNotice(notice, widthScale, heightScale, false, false);
    }

    /**
     * Renders a String in the Screen, in the style of a notice
     *
     * @param notice       The List of Strings to render
     * @param widthScale   The Scale/Value away from the center X to render at
     * @param heightScale  The Scale/Value away from the center Y to render at
     * @param useXAsActual Whether or not to use the widthScale as the actual X value
     * @param useYAsActual Whether or not to use the heightScale as the actual Y value
     */
    public void renderNotice(final List<String> notice, final float widthScale, final float heightScale, final boolean useXAsActual, final boolean useYAsActual) {
        if (notice != null && !notice.isEmpty()) {
            for (int i = 0; i < notice.size(); i++) {
                final String string = notice.get(i);
                renderString(string, (useXAsActual ? widthScale : (width / widthScale)) - (StringUtils.getStringWidth(string) / widthScale), (useYAsActual ? heightScale : (height / heightScale)) + (i * 10), 0xFFFFFF);
            }
        }
    }

    /**
     * Renders a String in the Screen, in the style of normal text
     *
     * @param text  The text to render to the screen
     * @param xPos  The X position to render the text at
     * @param yPos  The Y position to render the text at
     * @param color The color to render the text in
     */
    public void renderString(String text, float xPos, float yPos, int color) {
        getFontRenderer().drawWithShadow(text, xPos, yPos, color);
    }

    /**
     * Get the Current Mouse's X Coordinate Position
     *
     * @return The Mouse's X Coordinate Position
     */
    public int getMouseX() {
        return lastMouseX;
    }

    /**
     * Get the wrap width for elements to be wrapped by
     * <p>Mostly used as a helper method for wrapping String elements
     *
     * @return the wrap width for elements to be wrapped by
     */
    public int getWrapWidth() {
        return -1;
    }

    /**
     * Get the Current Mouse's Y Coordinate Position
     *
     * @return The Mouse's Y Coordinate Position
     */
    public int getMouseY() {
        return lastMouseY;
    }

    /**
     * Get the Current Font Renderer for this Screen
     *
     * @return The Current Font Renderer for this Screen
     */
    public TextRenderer getFontRenderer() {
        return minecraft.textRenderer != null ? minecraft.textRenderer : GuiUtils.getDefaultFontRenderer();
    }

    /**
     * Get the Current Font Height for this Screen
     *
     * @return The Current Font Height for this Screen
     */
    public int getFontHeight() {
        return getFontRenderer().fontHeight;
    }
}
