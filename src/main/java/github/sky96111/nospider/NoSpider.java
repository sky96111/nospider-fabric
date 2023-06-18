package github.sky96111.nospider;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.gui.screen.TitleScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoSpider implements ModInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoSpider.class);

    @Override
    public void onInitialize() {

        ScreenEvents.AFTER_INIT.register((client, screen, width, height) -> {
            if (screen instanceof TitleScreen) {
                LOGGER.info("TitleScreen init");
            }
        });

        
    }
}
