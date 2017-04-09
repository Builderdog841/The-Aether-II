package com.gildedgames.aether.common;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class ConfigAether
{

	public final Configuration configuration;

	private final ConfigCategory GENERAL, BIOMES, DIMENSIONS;

	private int aetherDimID;

	private int aetherBiomeID;

	private boolean displayInventoryPattern;

	private boolean displayPerformanceIndicator;

	public ConfigAether(File file)
	{
		this.configuration = new Configuration(file, true);

		this.GENERAL = this.configuration.getCategory(Configuration.CATEGORY_GENERAL);
		this.BIOMES = this.configuration.getCategory("Biome IDs");
		this.DIMENSIONS = this.configuration.getCategory("Dimension IDs");

		this.BIOMES.setRequiresMcRestart(true);
		this.DIMENSIONS.setRequiresMcRestart(true);

		this.loadAndSync();
	}

	private void loadAndSync()
	{
		this.aetherDimID = this.getInt(this.DIMENSIONS, "Aether Dimension ID", 3);
		this.aetherBiomeID = this.getInt(this.BIOMES, "Aether Biome ID", 237);

		this.displayInventoryPattern = this.getBoolean(this.GENERAL, "Display Inventory Pattern", true);
		this.displayPerformanceIndicator = this.getBoolean(this.GENERAL, "Display Performance Indicator", true);

		if (this.configuration.hasChanged())
		{
			this.configuration.save();
		}
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs)
	{
		if (eventArgs.getModID().equals(AetherCore.MOD_ID))
		{
			this.loadAndSync();
		}
	}

	private int getInt(ConfigCategory category, String name, int defaultValue)
	{
		return this.configuration.get(category.getName(), name, defaultValue).getInt();
	}

	private boolean getBoolean(ConfigCategory category, String name, boolean defaultValue)
	{
		return this.configuration.get(category.getName(), name, defaultValue).getBoolean();
	}

	public int getAetherDimID()
	{
		return this.aetherDimID;
	}

	public int getAetherBiomeID()
	{
		return this.aetherBiomeID;
	}

	public boolean getDisplayInventoryPattern()
	{
		return this.displayInventoryPattern;
	}

	public boolean getDisplayPerformanceIndicator()
	{
		return this.displayPerformanceIndicator;
	}
}
