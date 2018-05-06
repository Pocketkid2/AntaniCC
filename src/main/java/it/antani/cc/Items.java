package it.antani.cc;

import com.google.common.collect.ImmutableList;
import dan200.computercraft.ComputerCraft;
import dan200.computercraft.shared.peripheral.PeripheralType;
import dan200.computercraft.shared.peripheral.common.PeripheralItemFactory;
import ic2.api.item.IC2Items;
import static it.antani.cc.AntaniCCMod.MODID;
import it.antani.cc.turtle.ChunkloadingModem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Items {

    public static Item chunkloadingModem;
    public static final Logger logger = LogManager.getLogger(MODID);

    public static void createItems(){
        chunkloadingModem =  new ChunkloadingModem();
        ForgeRegistries.ITEMS.register(chunkloadingModem);
    }
    
    public static void initializeMod() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(Ingredient.fromStacks(IC2Items.getItem("te", "chunk_loader")));
        ingredients.add(Ingredient.fromStacks(PeripheralItemFactory.create( PeripheralType.AdvancedModem, null, 1 )));
        
        ForgeRegistries.RECIPES.register(new ShapelessRecipes("computercraft", 
                new ItemStack(chunkloadingModem),
                ingredients
        ).setRegistryName("chunkloading_modem_recipe"));
    }

    public static void registerRenderers(){
        logger.info("Registering: " + chunkloadingModem.getRegistryName().toString());
        ModelLoader.setCustomModelResourceLocation(chunkloadingModem, 0, 
                new ModelResourceLocation(chunkloadingModem.getRegistryName(), "inventory"));
    }

}
