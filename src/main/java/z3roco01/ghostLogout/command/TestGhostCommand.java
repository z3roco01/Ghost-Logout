package z3roco01.ghostLogout.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import z3roco01.ghostLogout.GhostLogout;
import z3roco01.ghostLogout.entity.GhostEntity;

import static net.minecraft.server.command.CommandManager.literal;

public class TestGhostCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("testghost")
                    .executes(context -> {
                        return cmd(context);
                    }));
        });;
    }

    private static int cmd(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        MinecraftServer     server = source.getServer();
        ServerWorld         world  = server.getWorld(World.OVERWORLD);
        if(world == null) {
            source.sendFeedback(Text.of("world == null"), false);
            return -1;
        }
        ServerPlayerEntity  player = source.getPlayerOrThrow();
        if(player == null) {
            source.sendFeedback(Text.of("player == null"), false);
            return -1;
        }

        GhostLogout.LOGGER.info(player.getName() + " tested ghost");

        GhostEntity ghost = new GhostEntity(world, player);
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        ghost.setPos(x, y, z);
        ghost.setYaw(player.getYaw());
        ghost.setPitch(player.getPitch());

        world.spawnEntity(ghost);

        source.sendFeedback(Text.of("spawned @ " + x + " " + y + " " + z), false);
        return 1;
    }
}
