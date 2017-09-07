package it.antani.cc.turtle;

import com.sun.tools.internal.xjc.ModelLoader;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import ic2.api.item.IC2Items;
import it.antani.cc.AntaniCCMod;
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

    // Annoying to create my own model. Let's use cc modem for now
    @SideOnly( Side.CLIENT )
    private  ModelResourceLocation m_leftOffModel = new ModelResourceLocation( "computercraft:advanced_turtle_modem_off_left", "inventory" );
    @SideOnly( Side.CLIENT )
    private  ModelResourceLocation m_rightOffModel = new ModelResourceLocation( "computercraft:advanced_turtle_modem_off_right", "inventory" );
    @SideOnly( Side.CLIENT )
    private  ModelResourceLocation m_leftOnModel = new ModelResourceLocation( "computercraft:advanced_turtle_modem_on_left", "inventory" );
    @SideOnly( Side.CLIENT )
    private  ModelResourceLocation m_rightOnModel = new ModelResourceLocation( "computercraft:advanced_turtle_modem_on_right", "inventory" );

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
        return IC2Items.getItem("te", "chunk_loader");
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
        boolean active = false;
        if( turtle != null )
        {
            NBTTagCompound turtleNBT = turtle.getUpgradeNBTData( side );
            if( turtleNBT.hasKey( "active" ) )
            {
                active = turtleNBT.getBoolean( "active" );
            }
        }
        Minecraft mc = Minecraft.getMinecraft();
        ModelManager modelManager = mc.getRenderItem().getItemModelMesher().getModelManager();
        if( side == TurtleSide.Left )
        {
            return Pair.of(
                    active ? modelManager.getModel( m_leftOnModel ) : modelManager.getModel( m_leftOffModel ),
                    null
            );
        }
        else
        {
            return Pair.of(
                    active ? modelManager.getModel( m_rightOnModel ) : modelManager.getModel( m_rightOffModel ),
                    null
            );
        }
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
