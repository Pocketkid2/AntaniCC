package it.antani.cc.impl.refinedstorage;

import com.raoulvdberge.refinedstorage.api.storage.IStorageCache;
import com.raoulvdberge.refinedstorage.api.util.IStackList;
import com.raoulvdberge.refinedstorage.tile.TileController;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import it.antani.cc.annotations.AcceptsTileEntity;
import it.antani.cc.annotations.LuaMethod;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    /**
     * Transfers the given amount of itemstack to the specified side and returns the number of transferred items.
     *  Expected args passed from the Lua interpreter are:
     *  String itemstackName : itemstack name to transfer
     *  Double numberOfItems : number of items to transfer
     *  String side : side where to transfer the item (north,south,up,down,est,west)
     *  @Returns the number of transferred items.
     */
    @LuaMethod("extract_item")
    public Object[] extractItem(TileController controller, IComputerAccess access, ILuaContext context, Object[] args) {

        if (controller.getNetwork() == null)
            return new Object[]{null, "Error, network not connected"};

        if(args.length != 3)
            return new Object[]{null,"Wrong number of arguments, expected arguments are : itemstackname,numberofitems,sideOfOutputInventory"};

        // First argument: the itemstack name
        String stackUnlocalizedName;
        Optional<ItemStack> stack;
        if (args[0] instanceof String) {
            stackUnlocalizedName = (String) args[0];
            stack = controller.getItemStorageCache().getList()
                    .getStacks()
                    .stream()
                    .filter(itemStack -> itemStack.getUnlocalizedName().equals(stackUnlocalizedName))
                    .findFirst();
        }
        else
            return new Object[]{null,"First parameter must be the itemstack name you need to retrieve"};


        if(!stack.isPresent())
            return new Object[]{null,"No element found for itemstack with name: " +  stackUnlocalizedName};

        // Second argument: the number of items to extract, at least 1 ...
        int numberOfItems = ((Double) args[1]).intValue();

        if(numberOfItems < 0)
            return new Object[]{null,"Can't transfer a negative number of items."};


        // ... and at most a full stack
        int count = Math.min(numberOfItems, stack.get().getMaxStackSize());

        //Third argument : the side of the controller (north,south,est,up,down,etc..) exposed to the destination inventory
        EnumFacing facing ;
        if (args[2] instanceof String) {
            String side = (String) args[2];
            facing = EnumFacing.valueOf(side.toUpperCase());
        }
        else
            return new Object[]{null,
                    "Third parameter must be the side where the item needs to be transferred." +
                    "Possibile values are: "+
                    Stream.of(EnumFacing.values()).map(Enum::name).collect( Collectors.joining( "," ) )};


        //Check the entity on the specified side
        TileEntity targetEntity = controller.getNode().getWorld().getTileEntity(controller.getNode().getPosition().offset(facing));
        if(targetEntity == null || !targetEntity.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite()))
            return new Object[]{null,"No inventory on the given side"};


        //Simulate an extraction of the itemstack
        IItemHandler handler = targetEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite());
        ItemStack extractedSim = Objects.requireNonNull(controller.getNode().getNetwork()).extractItem(stack.get(), count,null);
        if (extractedSim == null || extractedSim.getCount() == 0)
            return new Object[]{null,"Could not extract the specified item. Does it exist?"};


        int transferableAmount = extractedSim.getCount();

        //Simulate an insertion of the itemstack
        ItemStack insertedSim = ItemHandlerHelper.insertItemStacked(handler, extractedSim, true);

        if (insertedSim.getCount()>0 )
            transferableAmount -= insertedSim.getCount();

        //If the sum of both extraction and insertion bring us to a negative value, then the transfer cannot be done and thus must end here
        if (transferableAmount <= 0)
            return new Object[] { 0 };

        //The actual transfer

        ItemStack extracted = controller.getNode().getNetwork().extractItem(stack.get(), transferableAmount, null);
        assert extracted != null;
        //Put the itemstack in the destination inventory
        ItemHandlerHelper.insertItemStacked(handler, extracted, false);
        //Remove the itemstack of specified quantity from the network as it has been transferred
        controller.getNode().getItemStorageCache().getList().remove(stack.get(),transferableAmount);

        return new Object[] { transferableAmount };
    }
}
