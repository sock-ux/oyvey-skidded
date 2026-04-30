package me.alpha432.oyvey.features.modules.render;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.event.events.Render3DEvent;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

import java.awt.Color;

// IMPORTANT: your RenderUtil path might differ
import me.alpha432.oyvey.util.RenderUtil;

public class BlockESP extends Module {

    private final Block targetBlock = Blocks.DIAMOND_ORE;
    private final int range = 6;

    public BlockESP() {
        super("BlockESP", "Highlights blocks through walls", Category.RENDER, true, false, false);
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        if (mc.world == null || mc.player == null) return;

        BlockPos playerPos = mc.player.getBlockPos();

        for (BlockPos pos : BlockPos.iterate(
                playerPos.add(-range, -range, -range),
                playerPos.add(range, range, range))) {

            if (mc.world.getBlockState(pos).getBlock() == targetBlock) {
                drawBox(pos);
            }
        }
    }

    private void drawBox(BlockPos pos) {
        // TRY THIS FIRST
        RenderUtil.drawBoxESP(
                pos,
                new Color(0, 255, 255, 80),
                true,
                new Color(0, 255, 255, 255),
                1.5f,
                true,
                true,
                100
        );

        // IF ERROR → comment above and use this instead:
        // RenderUtil.drawBox(pos, new Color(0,255,255,80));
    }
}
