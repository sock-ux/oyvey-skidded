package me.alpha432.oyvey.features.modules.render;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.event.events.Render3DEvent;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

import java.awt.Color;
import me.alpha432.oyvey.util.RenderUtil;

public class BlockESP extends Module {

    public BlockESP() {
        super("BlockESP", "Highlights blocks", Category.RENDER, true, false, false);
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        if (mc.world == null || mc.player == null) return;

        BlockPos playerPos = mc.player.getBlockPos();
        int range = 6;

        for (BlockPos pos : BlockPos.iterate(
                playerPos.add(-range, -range, -range),
                playerPos.add(range, range, range))) {

            if (mc.world.getBlockState(pos).getBlock() == Blocks.DIAMOND_ORE) {
                drawBox(pos); // 👈 THIS calls your render method
            }
        }
    }

    private void drawBox(BlockPos pos) {
        RenderUtil.drawBox(pos, new Color(0,255,255,80)); // 👈 PUT IT HERE
    }
}
