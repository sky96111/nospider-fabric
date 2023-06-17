package github.sky96111.nospider.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(EntityType.class)
public abstract class CancelSpawn {

    @Inject(at = @At("HEAD"), method = "create(Lnet/minecraft/world/World;)Lnet/minecraft/entity/Entity;", cancellable = true)
    private <T> void StopSpawn(World world, CallbackInfoReturnable<T> cir) {

        if (this.toString().equals(EntityType.SPIDER.toString()) || this.toString().equals(EntityType.CAVE_SPIDER.toString())) {
            cir.setReturnValue(null);
        }
    }

}