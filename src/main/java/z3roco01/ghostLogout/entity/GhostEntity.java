package z3roco01.ghostLogout.entity;

import com.mojang.authlib.GameProfile;
import eu.pb4.polymer.api.entity.PolymerEntity;
import eu.pb4.polymer.api.entity.PolymerEntityUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Consumer;

public class GhostEntity extends Entity implements PolymerEntity {
    private static final TrackedData<Byte> PLAYER_MODEL_PARTS = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final EntityType<PlayerEntity> type = EntityType.PLAYER;
    public final String playerName;

    public GhostEntity(World world, ServerPlayerEntity player) {
        super(type, world);
        this.playerName = player.getGameProfile().getName();
        uuid = player.getGameProfile().getId();
        this.setUuid(player.getGameProfile().getId());
    }

    @Override
    public void onBeforeSpawnPacket(Consumer<Packet<?>> packetConsumer) {
        PlayerListS2CPacket packet = new PlayerListS2CPacket(PlayerListS2CPacket.Action.ADD_PLAYER, List.of());
        GameProfile gameProfile = new GameProfile(this.getUuid(), playerName);
        packet.getEntries().add(new PlayerListS2CPacket.Entry(gameProfile, 0, GameMode.ADVENTURE, Text.of(playerName), null));
        packetConsumer.accept(packet);
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return PolymerEntityUtils.createPlayerSpawnPacket(this);
    }

    @Override
    public void modifyTrackedData(List<DataTracker.Entry<?>> data) {
        data.add(new DataTracker.Entry<>(PLAYER_MODEL_PARTS, (byte)0b00111111));
    }

    @Override
    public EntityType<?> getPolymerEntityType() {
        return type;
    }

    @Override
    public EntityType<?> getPolymerEntityType(ServerPlayerEntity player) {
        return type;
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }
}
