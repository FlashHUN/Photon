package com.lowdragmc.photon.client.emitter.data.number.color;

import com.lowdragmc.lowdraglib.gui.texture.TransformTexture;
import com.lowdragmc.lowdraglib.utils.GradientColor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;

import java.util.function.Function;

/**
 * @author KilaBash
 * @date 2023/5/30
 * @implNote GradientColorTexture
 */
public class GradientColorTexture  extends TransformTexture {

    public final GradientColor gradientColor;

    public GradientColorTexture(GradientColor gradientColor) {
        this.gradientColor = gradientColor;
    }

    @Override
    @Environment(EnvType.CLIENT)
    protected void drawInternal(GuiGraphics graphics, int mouseX, int mouseY, float posx, float posy, int width, int height) {
        // render color bar
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        var mat = graphics.pose().last().pose();
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        Function<Float, Float> getXPosition = coordX -> posx + width * coordX;
        var yh = height + posy;
        RandomGradientColorTexture.drawGradient(posy, width, mat, buffer, getXPosition, yh, gradientColor);

        tesselator.end();
    }
}
