package it.antani.cc.turtle;

import com.sun.tools.internal.xjc.ModelLoader;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import ic2.api.item.IC2Items;
import it.antani.cc.AntaniCCMod;
import it.antani.cc.Items;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;

public class ChunkloadedTurtle implements ITurtleUpgrade {

    @Nonnull
    @Override
    public ResourceLocation getUpgradeID() {
        return new ResourceLocation(AntaniCCMod.MODID, "chunkloadedTurtle");
    }

    @Override
    public int getLegacyUpgradeID() {
        return -1;
    }

    @Nonnull
    @Override
    public String getUnlocalisedAdjective() {
        return "antanicc.turtle.chunkloaded";
    }

    @Nonnull
    @Override
    public TurtleUpgradeType getType() {
        return TurtleUpgradeType.Peripheral;
    }

    @Nullable
    @Override
    public ItemStack getCraftingItem() {
        return new ItemStack(Items.chunkloadingModem);
    }

    @Nullable
    @Override
    public IPeripheral createPeripheral(@Nonnull ITurtleAccess iTurtleAccess, @Nonnull TurtleSide turtleSide) {
        return new ChunkloaderPeripheral(iTurtleAccess);
    }

    @Nonnull
    @Override
    public TurtleCommandResult useTool(@Nonnull ITurtleAccess iTurtleAccess, @Nonnull TurtleSide turtleSide, @Nonnull TurtleVerb turtleVerb, @Nonnull EnumFacing enumFacing) {
        return TurtleCommandResult.failure();
    }

    @Nonnull
    @Override
    public Pair<IBakedModel, Matrix4f> getModel(@Nullable ITurtleAccess turtle, @Nonnull TurtleSide side) {
        // Honestly, this can be precalculated, but my geometry-fu is weak
        // and I don't like doing this.

        float xOffset = side == TurtleSide.Left ? -0.70625f : 0.70625f;

        // This will flip the modem
        float rot = side == TurtleSide.Left ? -1 : 1;

        Matrix4f transform = new Matrix4f(
                0.0f, 0.0f, 1.0f, -0.5f,
                0.0F, 1.0f, 0.0f, -0.5f,
                -1.0f, 0.0f, 0.0f, 0.5f,
                0.0f, 0.0f, 0.0f, 1.0f
        );

        IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(new ItemStack(Items.chunkloadingModem));

        transform.mul(new Matrix4f(
                0.7f * rot, 0.0f, 0.0f, 0.5f + xOffset,
                0.0f, 0.7f, 0.0f, 0.6f,
                0.0f, 0.0f, 0.7f * rot, 0.465f,
                0.0f, 0.0f, 0.0f, 1.0f
        ), transform);

        return Pair.of(model, transform);
    }

    @Override
    public void update(@Nonnull ITurtleAccess iTurtleAccess, @Nonnull TurtleSide turtleSide) {
        if(iTurtleAccess.getWorld().isRemote)
            return;
        IPeripheral peripheral = iTurtleAccess.getPeripheral(turtleSide);
        if(peripheral != null  && peripheral instanceof  ChunkloaderPeripheral){
            ChunkloaderPeripheral p = (ChunkloaderPeripheral) peripheral;
            p.update(iTurtleAccess);
            if(p.pollChanged()){
                iTurtleAccess.getUpgradeNBTData(turtleSide).setBoolean("active", p.isActive());
                iTurtleAccess.updateUpgradeNBTData(turtleSide);
            }
        }
    }
}
