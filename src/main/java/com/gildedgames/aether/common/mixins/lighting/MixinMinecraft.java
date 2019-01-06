package com.gildedgames.aether.common.mixins.lighting;

import com.gildedgames.aether.api.lighting.ILightingEngineProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.profiler.Profiler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft
{
	@Shadow
	@Final
	public Profiler profiler;

	@Shadow
	public WorldClient world;

	@Inject(method = "runTick", at = @At(value = "CONSTANT", args = "stringValue=levelRenderer", shift = At.Shift.BY, by = -3))
	private void onRunTick(CallbackInfo ci)
	{
		this.profiler.endStartSection("lighting");

		((ILightingEngineProvider) this.world).getLightingEngine().procLightUpdates();
	}

}
