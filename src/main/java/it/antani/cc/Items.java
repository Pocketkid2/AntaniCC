package it.antani.cc;

import dan200.computercraft.ComputerCraft;
import dan200.computercraft.shared.peripheral.PeripheralType;
import dan200.computercraft.shared.peripheral.common.PeripheralItemFactory;
import ic2.api.item.IC2Items;
import it.antani.cc.turtle.ChunkloadingModem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class Items {

    public static Item chunkloadingModem = new ChunkloadingModem();

    public static void createItems(){
        ForgeRegistries.ITEMS.register(chunkloadingModem);
        GameRegistry.addShapelessRecipe(
                chunkloadingModem.getRegistryName(),
                IC2Items.getItem("te", "chunk_loader").getItem().getRegistryName(),
                PeripheralItemFactory.create( PeripheralType.AdvancedModem, null, 1 )
        );
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenderers(){
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                .register(chunkloadingModem, 0, new ModelResourceLocation("antanicc:chunkloading_modem", "inventory"));
    }

}
