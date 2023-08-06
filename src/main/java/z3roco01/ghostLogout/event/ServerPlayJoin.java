package z3roco01.ghostLogout.event;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import z3roco01.ghostLogout.entity.GhostEntity;

public class ServerPlayJoin {
    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            Entity ghostEntity = server.getWorld(World.OVERWORLD).getEntity(player.getGameProfile().getId());
            if(ghostEntity != null && ghostEntity instanceof GhostEntity) {
                ghostEntity.remove(Entity.RemovalReason.DISCARDED);
            }
        });
    }
}
