package com.gildedgames.aether.api;

import com.gildedgames.aether.api.chunk.IChunkAttachment;
import com.gildedgames.aether.api.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.api.entity.spawning.ISpawningInfo;
import com.gildedgames.aether.api.orbis.IChunkRendererCapability;
import com.gildedgames.aether.api.orbis.IWorldObjectManager;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.api.world.ISectorAccess;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class AetherCapabilities
{
	@CapabilityInject(IPlayerAether.class)
	public static final Capability<IPlayerAether> PLAYER_DATA = null;

	@CapabilityInject(ISpawningInfo.class)
	public static final Capability<ISpawningInfo> ENTITY_SPAWNING_INFO = null;

	@CapabilityInject(IPlacementFlagCapability.class)
	public static final Capability<IPlacementFlagCapability> CHUNK_PLACEMENT_FLAG = null;

	@CapabilityInject(IChunkAttachment.class)
	public static final Capability<IChunkAttachment> CHUNK_ATTACHMENTS = null;

	@CapabilityInject(ISectorAccess.class)
	public static final Capability<ISectorAccess> SECTOR_ACCESS = null;

	@CapabilityInject(IWorldObjectManager.class)
	public static final Capability<IWorldObjectManager> WORLD_OBJECT_MANAGER = null;

	@CapabilityInject(IChunkRendererCapability.class)
	public static final Capability<IChunkRendererCapability> CHUNK_RENDERER = null;
}
