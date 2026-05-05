package com.astro.core.mixin.adastra;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;

import earth.terrarium.adastra.client.utils.SoundUtils;
import earth.terrarium.adastra.common.entities.vehicles.Lander;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Lander.class, remap = false)
public abstract class LanderAutoLandMixin extends Entity {

    @Shadow
    private float speed;
    @Shadow
    public boolean startedRocketSound;

    @Shadow
    protected abstract void spawnLanderParticles();

    public LanderAutoLandMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    /**
     * Suppress explosion on hard landing. the auto-landing handles deceleration
     * so the lander should never hit hard enough to explode under normal use.
     */
    @Overwrite(remap = false)
    public void explode() {}

    @Inject(method = "flightTick", at = @At("HEAD"), cancellable = true)
    private void autoLand(CallbackInfo ci) {
        Lander lander = (Lander) (Object) this;
        Level level = lander.level();
        int yPos = (int) lander.getY();
        int ground = level.getHeight(Heightmap.Types.WORLD_SURFACE, lander.getBlockX(), lander.getBlockZ());
        int distance = Math.max(0, yPos - ground);

        float stoppingThrust = 0.029f;
        int distanceOffset = 1;
        float gravity = -0.01f;

        float timeToImpact = (-speed + (float) Math.sqrt(
                speed * speed + 2.0f * gravity * (float) (distance - distanceOffset))) / gravity;
        float requiredDeceleration = (speed - 0.5f) / timeToImpact;
        float requiredThrust = requiredDeceleration + gravity;

        boolean shouldCounteract = requiredThrust <= stoppingThrust && requiredThrust > 0.0f && speed < 0.0f &&
                distance > 0;

        if (shouldCounteract) {
            speed += stoppingThrust;
            fallDistance *= 0.9f;
            spawnLanderParticles();
            if (level.isClientSide() && !startedRocketSound) {
                startedRocketSound = true;
                SoundUtils.playLanderSound(lander);
            }
            ci.cancel();
        }
    }
}
