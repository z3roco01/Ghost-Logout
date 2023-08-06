package z3roco01.ghostLogout;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import z3roco01.ghostLogout.command.TestGhostCommand;
import z3roco01.ghostLogout.event.ServerPlayJoin;
import z3roco01.ghostLogout.event.ServerPlayDisconnect;

public class GhostLogout implements ModInitializer {
    public static final String MOD_ID = "ghost_logout";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Starting init !");

		ServerPlayJoin.register();
		ServerPlayDisconnect.register();
		TestGhostCommand.register();

		LOGGER.info("Finished init !");
	}
}
