package dev.callmeecho.hollow.main.block.entity;

import dev.callmeecho.cabinetapi.particle.ParticleSystem;
import dev.callmeecho.cabinetapi.util.LootableInventoryBlockEntity;
import dev.callmeecho.hollow.main.registry.HollowBlockEntityRegistry;
import dev.callmeecho.hollow.main.registry.HollowBlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class StoneChestBlockEntity extends LootableInventoryBlockEntity {
    private static final ParticleSystem PARTICLE_SYSTEM = new ParticleSystem(
            new Vec3d(0.025F, 0.05F, 0.025F),
            new Vec3d(0.5, 1, 0.5),
            new Vec3d(0, 0, 0),
            15,
            1,
            true,
            ParticleTypes.LARGE_SMOKE
    );

    public StoneChestBlockEntity(BlockPos pos, BlockState state) {
        super(HollowBlockEntityRegistry.STONE_CHEST_BLOCK_ENTITY, pos, state, 27);
    }
    
    public void aboveBroken() {
        if (world == null) return;
        checkLootInteraction(null, true);
        Vec3d centerPos = pos.toCenterPos();
        for (ItemStack stack : inventory) {
            if (!stack.isEmpty()) {
                ItemEntity itemEntity = new ItemEntity(world, centerPos.getX(), centerPos.getY() + 0.5, centerPos.getZ(), stack);
                world.spawnEntity(itemEntity);
            }
        }
        
        inventory.clear();
        notifyListeners();
        
        PARTICLE_SYSTEM.tick(world, pos);
    }

    public ActionResult use(PlayerEntity player, Hand hand, Direction side) {
        if (player.getStackInHand(hand).isEmpty()) return ActionResult.PASS;
        if (player.getStackInHand(hand).isOf(HollowBlockRegistry.STONE_CHEST_LID.asItem()) && side.equals(Direction.UP)) return ActionResult.PASS;


        int slot = -1;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).isEmpty()) {
                slot = i;
                break;
            }
        }

        if (slot == -1) return ActionResult.PASS;

        setStack(slot, player.getStackInHand(hand));
        notifyListeners();
        player.setStackInHand(hand, ItemStack.EMPTY);
        return ActionResult.CONSUME;
    }
}
