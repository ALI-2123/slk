package dev.blade;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.text.Text;

/** A red and black fade button. */
public class BladeButton extends ButtonBase {
    private final Runnable action;
    private boolean selected;

    public BladeButton(int x, int y, int width, int height, String label, Runnable action) {
        super(x, y, width, height, Text.literal(label));
        this.action = action;
    }

    public BladeButton selected(boolean value) {
        this.selected = value;
        return this;
    }

    public void setLabel(String label) {
        setMessage(Text.literal(label));
    }

    @Override
    protected void renderWidget(DrawContext ctx, int mouseX, int mouseY, float delta) {
        boolean hot = isHovered() || selected;
        int x = getX();
        int y = getY();
        ctx.fillGradient(x, y, x + getWidth(), y + getHeight(), hot ? 0xFFE01010 : 0xFF6A0000, hot ? 0xFF8A0000 : 0xFF1A0000);
        ctx.fill(x, y + getHeight() - 1, x + getWidth(), y + getHeight(), hot ? Theme.HOT : 0xFF8A0000);
        MinecraftClient mc = MinecraftClient.getInstance();
        ctx.drawCenteredTextWithShadow(mc.textRenderer, getMessage(), x + getWidth() / 2, y + (getHeight() - 8) / 2, Theme.TEXT);
    }

    @Override
    protected void fire() {
        action.run();
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        appendDefaultNarrations(builder);
    }
}
