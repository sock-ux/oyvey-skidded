import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.block.Blocks;
import java.util.ArrayList;
import java.util.List;

public class BlockESPManager {
    private final MinecraftClient client = MinecraftClient.getInstance();
    private final List<BlockPos> highlightedBlocks = new ArrayList<>();
    
    // 1. SCANNING STEP: Call this when entering a world or when chunks load
    public void scanArea(int radius) {
        if (client.world == null || client.player == null) return;
        
        highlightedBlocks.clear();
        BlockPos playerPos = client.player.getBlockPos();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = playerPos.add(x, y, z);
                    // Targeted block (e.g., Diamond Ore)
                    if (client.world.getBlockState(pos).isOf(Blocks.DIAMOND_ORE)) {
                        highlightedBlocks.add(pos);
                    }
                }
            }
        }
    }

    // 2. RENDERING STEP: Call this inside a WorldRender event
    public void onRender(MatrixStack matrices) {
        if (highlightedBlocks.isEmpty()) return;

        // X-Ray Effect: Disable depth testing to see through walls
        RenderSystem.disableDepthTest();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(VertexFormat.DrawMode.LINES, VertexFormats.POSITION_COLOR);

        for (BlockPos pos : highlightedBlocks) {
            // Apply padding (0.02) to prevent "flickering" against the block face
            Box box = new Box(pos).expand(0.02);
            
            // Draw a green box (R:0, G:1, B:0, Alpha: 0.5)
            WorldRenderer.drawBox(matrices, buffer, 
                box.minX, box.minY, box.minZ, 
                box.maxX, box.maxY, box.maxZ, 
                0f, 1f, 0f, 0.5f);
        }

        tessellator.draw();
        RenderSystem.enableDepthTest(); // Re-enable so the rest of the game renders normally
    }
}

