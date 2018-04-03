package com.gildedgames.aether.common.world.aether.island.gen;

import com.gildedgames.aether.api.util.NoiseUtil;
import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class IslandGeneratorArcticPeaks implements IIslandGenerator
{
	// Resolution of the evalNormalised for a chunk. Should be a power of 2.
	private static final int NOISE_XZ_SCALE = 4;

	// Number of samples done per chunk.
	private static final int NOISE_SAMPLES = NOISE_XZ_SCALE + 1;

	public static double interpolate(final double[] data, final int x, final int z)
	{
		final double x0 = (double) x / NOISE_XZ_SCALE;
		final double z0 = (double) z / NOISE_XZ_SCALE;

		final int integerX = (int) Math.floor(x0);
		final double fractionX = x0 - integerX;

		final int integerZ = (int) Math.floor(z0);
		final double fractionZ = z0 - integerZ;

		final double a = data[integerX + (integerZ * NOISE_SAMPLES)];
		final double b = data[integerX + ((integerZ + 1) * NOISE_SAMPLES)];
		final double c = data[integerX + 1 + (integerZ * NOISE_SAMPLES)];
		final double d = data[integerX + 1 + ((integerZ + 1) * NOISE_SAMPLES)];

		return (1.0 - fractionX) * ((1.0 - fractionZ) * a + fractionZ * b) +
				fractionX * ((1.0 - fractionZ) * c + fractionZ * d);
	}

	public static double[] generateNoise(final OpenSimplexNoise noise, final IIslandData island, final int chunkX, final int chunkZ)
	{
		final double posX = chunkX * 16;
		final double posZ = chunkZ * 16;

		final double minX = island.getBounds().getMinX();
		final double minZ = island.getBounds().getMinZ();

		final double[] data = new double[NOISE_SAMPLES * NOISE_SAMPLES];

		// Generate half-resolution evalNormalised
		for (int x = 0; x < NOISE_SAMPLES; x++)
		{
			// Creates world coordinate and normalized evalNormalised coordinate
			final double worldX = posX - (x == 0 ? NOISE_XZ_SCALE - 1 : 0) + (x * (16D / NOISE_SAMPLES));
			final double nx = (worldX + minX) / 300.0D;

			for (int z = 0; z < NOISE_SAMPLES; z++)
			{
				// Creates world coordinate and normalized evalNormalised coordinate
				final double worldZ = posZ - (z == 0 ? NOISE_XZ_SCALE - 1 : 0) + (z * (16.0D / NOISE_SAMPLES));
				final double nz = (worldZ + minZ) / 300.0D;

				data[x + (z * NOISE_SAMPLES)] = NoiseUtil.genNoise(noise, nx, nz);
			}
		}

		return data;
	}

	public static double ridgeNoise(final OpenSimplexNoise noise, final double nx, final double ny)
	{
		return 2 * (0.5 - Math.abs(0.5 - noise.eval(nx, ny)));
	}

	@Override
	public void genIslandForChunk(final OpenSimplexNoise noise, final IBlockAccess access, final ChunkPrimer primer, final IIslandData island, final int chunkX,
			final int chunkZ)
	{
		final double[] heightMap = generateNoise(noise, island, chunkX, chunkZ);

		final Biome biome = access.getBiome(new BlockPos(chunkX * 16, 0, chunkZ * 16));

		final IBlockState coastBlock = ((BiomeAetherBase) biome).getCoastalBlock();
		final IBlockState stoneBlock = BlocksAether.holystone.getDefaultState();

		final int posX = chunkX * 16;
		final int posZ = chunkZ * 16;

		final double centerX = island.getBounds().getCenterX();
		final double centerZ = island.getBounds().getCenterZ();

		final double radiusX = island.getBounds().getWidth() / 2.0;
		final double radiusZ = island.getBounds().getLength() / 2.0;

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				final int worldX = posX + x;
				final int worldZ = posZ + z;

				final double sample = interpolate(heightMap, x, z);

				final double distX = Math.abs((centerX - worldX) * (1.0 / radiusX));
				final double distZ = Math.abs((centerZ - worldZ) * (1.0 / radiusZ));

				// Get distance from center of Island
				final double dist = Math.sqrt(distX * distX + distZ * distZ) / 1.0D;

				final double heightSample = sample + 1.0 - dist;

				final double bottomMaxY = 100;

				final double topHeight = 146;

				final double cutoffPoint = 0.325;

				final double normal = NoiseUtil.normalise(sample);
				final double cutoffPointDist = Math.abs(cutoffPoint - heightSample);

				double bottomHeightMod = Math.pow(normal, 0.2);

				final double newHeightSample = Math.min(1.1, Math.pow(heightSample, 4.0) * 0.55);

				final double bottomHeight = 100;

				if (heightSample > cutoffPoint)
				{
					if (heightSample < cutoffPoint + 0.1)
					{
						bottomHeightMod = cutoffPointDist * heightSample * 16.0;
					}

					for (int y = (int) bottomMaxY; y > bottomMaxY - (bottomHeight * bottomHeightMod); y--)
					{
						if (y < 0)
						{
							continue;
						}

						if (coastBlock != null && heightSample < cutoffPoint + 0.05 && y == 100)
						{
							primer.setBlockState(x, y, z, coastBlock);
						}
						else
						{
							primer.setBlockState(x, y, z, stoneBlock);
						}
					}

					final double maxY = bottomMaxY + (newHeightSample * topHeight);

					for (int y = (int) bottomMaxY; y < maxY; y++)
					{
						if (coastBlock != null && (newHeightSample < cutoffPoint + 0.05 && y == 100))
						{
							primer.setBlockState(x, y, z, coastBlock);
						}
						else
						{
							if (newHeightSample > 0.7 && y > maxY - 8)
							{
								primer.setBlockState(x, y, z, Blocks.SNOW.getDefaultState());
							}
							else
							{
								primer.setBlockState(x, y, z, stoneBlock);
							}
						}
					}
				}
			}
		}
	}

}
