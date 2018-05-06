package it.antani.cc.turtle;

import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.shared.peripheral.modem.WirelessModemPeripheral;
import it.antani.cc.AntaniCCMod;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class ChunkloaderPeripheral extends WirelessModemPeripheral {

    private boolean attached = false;
    private ForgeChunkManager.Ticket ticket;

    private BlockPos currentPos;
    private World currentWorld;
    private static final int radius = 6;

    private static final Logger logger = LogManager.getLogger(AntaniCCMod.MODID);


    ChunkloaderPeripheral(ITurtleAccess turtle) {
        super(true);
    }

    @Override
    public void attach(@Nonnull IComputerAccess computer) {
        super.attach(computer);
        attached = true;
    }

    @Override
    public void detach(@Nonnull IComputerAccess computer) {
        super.detach(computer);
        logger.info("detached");
        ForgeChunkManager.releaseTicket(ticket);
        attached = false;
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        return false;
    }

    void update(ITurtleAccess access){
        if(!attached || access.getWorld().isRemote || currentPos == access.getPosition()){
            return;
        }
        currentPos = access.getPosition();
        currentWorld = access.getWorld();

        if(ticket == null) {
            ticket = ForgeChunkManager.requestTicket(AntaniCCMod.instance, access.getWorld(), ForgeChunkManager.Type.NORMAL);
        }
        if(ticket.world != access.getWorld()){
            // Aw shit world changed.
            ForgeChunkManager.releaseTicket(ticket);
            ticket = ForgeChunkManager.requestTicket(AntaniCCMod.instance, access.getWorld(), ForgeChunkManager.Type.NORMAL);
        }

        Set<ChunkPos> chunksAround = getChunksAround();
        chunksAround.add(new ChunkPos(currentPos));
        Set<ChunkPos> forcedChunks = ticket.getChunkList();
        for(ChunkPos chunk : forcedChunks ){
            if(!chunksAround.contains(chunk)){
                ForgeChunkManager.unforceChunk(ticket, chunk);
            }
        }
        for(ChunkPos chunk : chunksAround) {
            if (!forcedChunks.contains(chunk)) {
                ForgeChunkManager.forceChunk(ticket, chunk);
            }
        }
    }

    private Set<ChunkPos> getChunksAround() {
        HashSet<ChunkPos> chunkList = new HashSet<>();
        ChunkPos currentChunk = new ChunkPos(currentPos);
        for (int chunkX = currentChunk.getXStart() - radius; chunkX <= currentChunk.getXStart() + radius; chunkX++) {
            for (int chunkZ = currentChunk.getZStart() - radius; chunkZ <= currentChunk.getZStart() + radius; chunkZ++) {
                chunkList.add(new ChunkPos(new BlockPos(chunkX,0, chunkZ)));
            }
        }
        return chunkList;
    }

    @Nonnull
    @Override
    public World getWorld() {
        return currentWorld;
    }

    @Nonnull
    @Override
    public Vec3d getPosition() {
        return new Vec3d(
                (double)currentPos.getX(),
                (double)currentPos.getY(),
                (double)currentPos.getZ()
        );
    }
}
