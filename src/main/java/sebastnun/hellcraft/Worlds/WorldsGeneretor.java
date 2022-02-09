package sebastnun.hellcraft.Worlds;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class WorldsGeneretor extends ChunkGenerator {

    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
        ChunkData chunk = createChunkData(world);


        for (int X = 0; X < 16; X++) {
            for (int Z = 0; Z < 16; Z++) {
                for (int y = 0; y <= 10; y++) {
                    chunk.setBlock(X, y, Z, Material.AIR);
                }
            }
        }

        return chunk;
    }


    public byte[][] generateBlockSections(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomeGrid){
        byte[][] result = new byte[world.getMaxHeight() / 16][]; //world height / chunk part height (=16, look above)
        this.setBlock(result,(int)world.getSpawnLocation().getX(),(int)(world.getSpawnLocation().getY()-1),(int) world.getSpawnLocation().getZ(),
                (byte) Material.BEDROCK.getId());

        return result;
    }

    private void setBlock(byte[][] result, int x, int y, int z, byte blkid) {
        // is this chunk part already initialized?
        if (result[y >> 4] == null) {
            // Initialize the chunk part
            result[y >> 4] = new byte[4096];
        }
        // set the block (look above, how this is done)
        result[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = blkid;
    }

}
