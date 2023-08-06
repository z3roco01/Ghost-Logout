package z3roco01.ghostLogout.event;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import z3roco01.ghostLogout.GhostLogout;
import z3roco01.ghostLogout.entity.GhostEntity;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerPlayDisconnect {
    private static final ScheduledExecutorService scheduledExecuter = Executors.newSingleThreadScheduledExecutor();
    public static void register() {
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            World world = server.getWorld(World.OVERWORLD);
            GhostEntity ghost = new GhostEntity(world, player);

            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();
            ghost.setPos(x, y, z);
            ghost.setYaw(player.getYaw());
            ghost.setPitch(player.getPitch());
            GhostLogout.LOGGER.info("spawned ghost @ " + x + " " + y + " " + z + " with uuid " + ghost.getUuid().toString() + " and name " + ghost.getName());

            scheduledExecuter.schedule(() ->{
                synchronized(world) {
                    world.spawnEntity(ghost);
                }
            }, 100, TimeUnit.MILLISECONDS);
        });
    }
}
