package dev.blade;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.text.Text;

/** The title screen: red to black fade with the black-to-red blade in the middle. */
public class BladeTitleScreen extends BladeScreen {
    public BladeTitleScreen() {
        super(Text.literal("Blade"));
    }

    @Override
    protected void init() {
        int w = 200;
        int x = (width - w) / 2;
        int y = height / 2 - 30;
        MinecraftClient mc = MinecraftClient.getInstance();
        addDrawableChild(new BladeButton(x, y, w, 22, "Singleplayer", () -> mc.setScreen(new SelectWorldScreen(this))));
        addDrawableChild(new BladeButton(x, y + 26, w, 22, "Multiplayer", () -> mc.setScreen(new MultiplayerScreen(this))));
        addDrawableChild(new BladeButton(x, y + 52, w, 22, "Blade: friends, cosmetics, HUD", () -> mc.setScreen(new BladeMenuScreen(BladeMenuScreen.Tab.FRIENDS))));
        addDrawableChild(new BladeButton(x, y + 78, w / 2 - 2, 22, "Options", () -> mc.setScreen(new OptionsScreen(this, mc.options))));
        addDrawableChild(new BladeButton(x + w / 2 + 2, y + 78, w / 2 - 2, 22, "Quit", mc::scheduleStop));
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public void renderBackground(DrawContext ctx, int mouseX, int mouseY, float delta) {
        // Blade draws its own background in render().
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        Theme.background(ctx, width, height);
        Theme.blade(ctx, width / 2, 8, height - 16, 9);
        Compat.drawScaledCentered(ctx, textRenderer, "BLADE", width / 2, Math.max(6, height / 2 - 80), 3f, Theme.TEXT);
        super.render(ctx, mouseX, mouseY, delta);
    }
}
