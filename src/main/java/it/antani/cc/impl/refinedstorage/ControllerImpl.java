package it.antani.cc.impl.refinedstorage;

import com.raoulvdberge.refinedstorage.api.storage.IStorageCache;
import com.raoulvdberge.refinedstorage.api.util.Action;
import com.raoulvdberge.refinedstorage.api.util.IStackList;
import com.raoulvdberge.refinedstorage.tile.TileController;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import it.antani.cc.annotations.AcceptsTileEntity;
import it.antani.cc.annotations.LuaMethod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

/**
 * Connects to a refined storage controller
 */
@AcceptsTileEntity(TileController.class)
public class ControllerImpl {

    /**
     * List all items in the system
     *
     * Returns a table with stack - quantity
     */
    @LuaMethod("list_items")
    public Object[] listItems(TileController controller, IComputerAccess access, ILuaContext context, Object[] args){
        IStorageCache<ItemStack> cache = controller.getItemStorageCache();
        IStackList<ItemStack> list = cache.getList();
        Map<String, Integer> all_items = new HashMap<>();
        for(ItemStack stack: list.getStacks()){
            if(all_items.containsKey(stack.getUnlocalizedName())){
                int count = stack.getCount() + all_items.get(stack.getUnlocalizedName());
                all_items.put(stack.getUnlocalizedName(), count);
            }else{
                all_items.put(stack.getUnlocalizedName(), stack.getCount());
            }
        }

        return new Object[] {all_items};
    }
}
