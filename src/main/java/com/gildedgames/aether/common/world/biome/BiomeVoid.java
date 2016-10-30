package com.gildedgames.aether.common.world.biome;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.world.dimensions.aether.biomes.BiomeAetherBase;
import net.minecraft.world.biome.Biome;

public class BiomeVoid extends BiomeAetherBase
{

	public BiomeVoid()
	{
		super(new Biome.BiomeProperties("Aether Void").setRainDisabled().setTemperature(0.5f), AetherCore.getResource("aether_void"));
	}

}
