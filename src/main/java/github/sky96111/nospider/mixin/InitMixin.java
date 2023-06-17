package github.sky96111.nospider.mixin;

import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class InitMixin {
    @Inject(at = @At("HEAD"), method = "init()V")
    private void init(CallbackInfo ci) {
        Log.info(LogCategory.LOG, "Init NoSpider Mixin");
    }
}
