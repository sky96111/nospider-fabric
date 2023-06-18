package github.sky96111.nospider.mixin;

import github.sky96111.nospider.NoSpider;
import net.minecraft.client.gui.screen.TitleScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class InitMixin {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoSpider.class);

    @Inject(at = @At("HEAD"), method = "init()V")
    private void init(CallbackInfo ci) {
        LOGGER.info("Mixin init");
    }
}
