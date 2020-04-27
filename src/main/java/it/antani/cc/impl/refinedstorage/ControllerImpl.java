package it.antani.cc.impl.refinedstorage;

import com.raoulvdberge.refinedstorage.api.storage.IStorageCache;
import com.raoulvdberge.refinedstorage.api.util.Action;
import com.raoulvdberge.refinedstorage.api.util.IStackList;
import com.raoulvdberge.refinedstorage.tile.TileController;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.shared.computer.blocks.TileComputer;
import dan200.computercraft.shared.computer.blocks.TileComputerBase;
import dan200.computercraft.shared.turtle.blocks.TileTurtle;
import dan200.computercraft.shared.util.InventoryUtil;
import it.antani.cc.IndustrialPeripheralContext;
import it.antani.cc.annotations.AcceptsTileEntity;
import it.antani.cc.annotations.LuaMethod;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
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
    public Object[] listItems(IndustrialPeripheralContext<TileController> ctx, IComputerAccess access, ILuaContext context, Object[] args){
        IStorageCache<ItemStack> cache = ctx.getTileEntity().getItemStorageCache();
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
     *  (Optional)String side : side of the caller computer/turtle where the item needs to be transferred to (north,south,up,down,east,west)
     *  Last parameter is mandatory for computers (as they lack inventory) but not for turtles, when not specified the current turtle's selected slot is used instead.
     *  Returns the number of transferred items.
     */
    @LuaMethod("extract_item")
    public Object[] extractItem(IndustrialPeripheralContext<TileController> ctx, IComputerAccess access, ILuaContext context, Object[] args) throws LuaException {

        TileController controller = ctx.getTileEntity();
        if (controller.getNetwork() == null)
            throw new LuaException("Error, network not connected");

        if (args.length > 3 || args.length < 2)
            throw new LuaException("Wrong number of arguments, expected arguments are : itemstackname,numberofitems[,sideofoutputinventory]");

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
        } else
            throw new LuaException("First parameter must be the itemstack name you need to retrieve");


        if (!stack.isPresent())
            throw new LuaException("No element found for itemstack with name: " + stackUnlocalizedName);

        // Second argument: the number of items to extract, at least 1 ...
        int numberOfItems = ((Double) args[1]).intValue();

        if (numberOfItems <= 0)
            throw new LuaException("Can't transfer a negative or zero number of items.");


        // ... and at most a full stack
        int count = Math.min(numberOfItems, stack.get().getMaxStackSize());

        //Third argument : Side of the computer/turtle (north,south,east,up,down,etc..) where item needs to be transferred
        EnumFacing facing = null;
        if (args.length > 2 && args[2] instanceof String) {
            String side = (String) args[2];
            facing = EnumFacing.valueOf(side.toUpperCase());
        }


        for (EnumFacing enumFacing : EnumFacing.values()) {
            //Let's find the caller entity
            TileEntity te = controller.getNode().getWorld().getTileEntity(controller.getNode().getPosition().offset(enumFacing));
            if (te instanceof TileComputerBase) {
                TileComputerBase tileComputerBase = (TileComputerBase) te;

                if (tileComputerBase.getComputerID() == access.getID()) {
                    //this is the computer/turtle that called this method

                    if (facing != null) {
                        //Side has been specified, try to transfer the item to inventory at (north|south|east|west|up|down) of the invoking entity

                        final EnumFacing finalFacing;
                        //Facing is relative to the turtle/computer, but API uses absolute ones, so we need to convert the first in the second kind
                        //This is not needed when a vertical facing has been specified (UP|DOWN)
                        if(facing.getAxis().isHorizontal()) {
                            EnumFacing tileComputerFacing = tileComputerBase.getDirection();
                            float entityFacingAngle = tileComputerFacing.getHorizontalAngle(); //Absolute angle from the entity
                            float relativeSideAngle = facing.getHorizontalAngle(); //Relative angle of the specified side
                            finalFacing = EnumFacing.fromAngle(entityFacingAngle + relativeSideAngle % 360); //Absolute angle of the specified side
                        }
                        else
                            finalFacing = facing;

                        return transferItemToInventory(stack.get(), count, controller, tileComputerBase, finalFacing);
                    }

                    else if (tileComputerBase instanceof TileTurtle) {
                        //so this is a turtle
                        TileTurtle tileTurtle = (TileTurtle) tileComputerBase;

                        //if side is not specified, try to transfer the item into the selected slot of the turtle, only if there is space available
                        ItemStack stackInSelectedSlot = tileTurtle.getItemHandler().getStackInSlot(tileTurtle.getAccess().getSelectedSlot());

                        if (!stackInSelectedSlot.isEmpty() && !stackInSelectedSlot.getUnlocalizedName().equals(stack.get().getUnlocalizedName()))
                            //Can't put stuff in other stuff's slot, abort
                            throw new LuaException("Can't transfer item " + stackUnlocalizedName + " in slot " + tileTurtle.getAccess().getSelectedSlot() + " : already occupied by " + stackInSelectedSlot.getUnlocalizedName());

                        if(stackInSelectedSlot.getCount() == stackInSelectedSlot.getMaxStackSize())
                            throw new LuaException("Can't transfer item " + stackUnlocalizedName + " in slot " + tileTurtle.getAccess().getSelectedSlot() + " : slot is already full.");


                        int transferableAmount = Math.min(count,stackInSelectedSlot.getMaxStackSize() - stackInSelectedSlot.getCount());

                        if (transferableAmount > 0) {
                            ItemStack extracted = Objects.requireNonNull(controller.getNode().getNetwork()).extractItem(stack.get(), transferableAmount, Action.PERFORM);
                            assert extracted != null;
                            InventoryUtil.storeItems(extracted, tileTurtle.getItemHandler(), tileTurtle.getAccess().getSelectedSlot());
                        }
                        else{
                            throw new LuaException("Can't transfer item " + stackUnlocalizedName + " in slot " + tileTurtle.getAccess().getSelectedSlot());
                        }

                    } else if (tileComputerBase instanceof TileComputer) {
                        //Computers can try to transfer items in adjacent inventories ONLY if the side is specified
                        throw new LuaException("Computers do not have inventory, you must specify a side of the computer whose is facing an inventory as third parameter. Possibile values are: " +
                                Stream.of(EnumFacing.values()).map(Enum::name).collect(Collectors.joining(",")));
                    }
                }
            }
        }

        return new Object[]{0};
    }

    /**
      Transfers item of specified amount through the use of the ItemHandlerHelper API
      Returns an Object array containing the number of successful transferred items
     */
   private Object[] transferItemToInventory(ItemStack itemStack, int amount, TileController controller, TileEntity tileEntity, EnumFacing facing) throws LuaException {

        BlockPos tileEntityPos = tileEntity.getPos();
        BlockPos targetEntityPos = tileEntityPos.offset(facing.getAxis().isHorizontal()? facing.getOpposite():facing);

        TileEntity targetEntity = tileEntity.getWorld().getTileEntity(targetEntityPos);

        if(targetEntity == null || !targetEntity.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite()))
            throw new LuaException("No inventory on the given side");

        IItemHandler handler = targetEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite());

        int transferableAmount = numberOfSimulatedTransferredItems(itemStack,amount,handler,controller);

        if(transferableAmount > 0 ){
            ItemStack extracted = Objects.requireNonNull(controller.getNode().getNetwork()).extractItem(itemStack, transferableAmount, Action.PERFORM);
            assert extracted != null;
            ItemHandlerHelper.insertItemStacked(handler, extracted, false);
        }
        return new Object[] { transferableAmount };
    }

    /**
      Simulates transfer of items through the use of the ItemHandlerHelper API
      Returns the maximum number of items that could be transferred with the simulation
     */
    private int numberOfSimulatedTransferredItems(ItemStack stack, int count, IItemHandler handler, TileController controller){

        //Simulate an extraction of the itemstack
        ItemStack extractedSim = Objects.requireNonNull(controller.getNode().getNetwork()).extractItem(stack, count,Action.SIMULATE);

        //If the simulation fails and the item cannot be extracted from the network, stop here
        if (extractedSim == null || extractedSim.getCount() == 0)
            return 0;

        int transferableAmount = extractedSim.getCount();

        //Simulate an insertion of the itemstack
        ItemStack insertedSim = ItemHandlerHelper.insertItemStacked(handler, extractedSim, true);

        if (insertedSim.getCount()>0 )
            transferableAmount -= insertedSim.getCount();

        return Math.max(transferableAmount, 0);

    }
}
