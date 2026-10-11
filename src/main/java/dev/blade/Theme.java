package dev.blade;

import net.minecraft.client.gui.DrawContext;

/** Red and black fade colors, plus the black-to-red blade drawn in the middle of Blade screens. */
public final class Theme {
    public static final int TEXT = 0xFFFFFFFF;
    public static final int DIM = 0xFFBBA9A6;
    public static final int HOT = 0xFFFF4438;

    private Theme() {}

    public static int lerp(int a, int b, float t) {
        t = Math.max(0f, Math.min(1f, t));
        int out = 0;
        for (int shift = 24; shift >= 0; shift -= 8) {
            int ca = (a >>> shift) & 255;
            int cb = (b >>> shift) & 255;
            out |= ((int) (ca + (cb - ca) * t) & 255) << shift;
        }
        return out;
    }

    /** Red at the top fading into black at the bottom. */
    public static void background(DrawContext ctx, int w, int h) {
        ctx.fillGradient(0, 0, w, h, 0xFFC00000, 0xFF000000);
        ctx.fillGradient(0, h / 2, w, h, 0x00000000, 0x99000000);
    }

    /** A blade pointing up, fading from black to red along its length. */
    public static void blade(DrawContext ctx, int cx, int top, int height, int half) {
        ctx.fillGradient(cx - half * 3, top, cx + half * 3, top + height, 0x00FF2A1A, 0x33FF2A1A);
        for (int y = 0; y < height; y += 2) {
            float t = y / (float) height;
            int w;
            float tone;
            if (t < 0.12f) {
                w = Math.max(1, Math.round(half * (t / 0.12f)));
                tone = t / 0.7f;
            } else if (t < 0.70f) {
                w = half;
                tone = t / 0.7f;
            } else if (t < 0.745f) {
                w = Math.round(half * 3.2f);
                tone = 1f;
            } else if (t < 0.95f) {
                w = Math.max(2, Math.round(half * 0.36f));
                tone = 0.3f;
            } else {
                w = Math.round(half * 0.7f);
                tone = 0.8f;
            }
            int left = lerp(0xFF000000, 0xFF8A0000, tone);
            int right = lerp(0xFF3A0000, 0xFFFF4438, tone);
            ctx.fill(cx - w, top + y, cx, top + y + 2, left);
            ctx.fill(cx, top + y, cx + w, top + y + 2, right);
            if (t >= 0.12f && t < 0.70f) ctx.fill(cx, top + y, cx + 1, top + y + 2, 0xFF000000);
        }
    }

    public static void panel(DrawContext ctx, int x, int y, int w, int h) {
        ctx.fill(x, y, x + w, y + h, 0xC0000000);
        ctx.fillGradient(x, y, x + w, y + 2, 0xFFFF4438, 0xFFD40000);
        ctx.fill(x, y + h - 1, x + w, y + h, 0xFF8A0000);
        ctx.fill(x, y, x + 1, y + h, 0xFF8A0000);
        ctx.fill(x + w - 1, y, x + w, y + h, 0xFF8A0000);
    }

    /** Background for HUD modules. */
    public static void hudBox(DrawContext ctx, int x, int y, int w, int h) {
        ctx.fillGradient(x, y, x + w, y + h, 0xB0450000, 0xB0000000);
        ctx.fill(x, y, x + 2, y + h, HOT);
    }

    /** Light red and black tint drawn over vanilla screens such as the pause menu. */
    public static void overlay(DrawContext ctx, int w, int h) {
        ctx.fillGradient(0, 0, w, h, 0x50C00000, 0x70000000);
        ctx.fillGradient(w / 2 - 1, 0, w / 2 + 1, h, 0x00FF2A1A, 0x55FF2A1A);
    }
}
