package github.sky96111.nospider.mixin;

import github.sky96111.nospider.NoSpider;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class EntityRenderBlocker {

    @Inject(at = @At("HEAD"), method = "isVisible", cancellable = true)
    private void renderEntity(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if (NoSpider.isBlockedType(entity.getType())) {
            cir.setReturnValue(false);
        }
    }

}