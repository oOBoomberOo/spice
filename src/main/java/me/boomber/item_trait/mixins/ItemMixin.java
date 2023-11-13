package me.boomber.item_trait.mixins;

import me.boomber.item_trait.trait.TraitRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "use", at = @At("HEAD"))
    public void use(Level level, Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        var itemStack = player.getItemInHand(interactionHand);
        TraitRegistry.INSTANCE.perform(itemStack, it -> it.onUse(level, player, interactionHand, itemStack));
    }

    @Inject(method = "mineBlock", at = @At("HEAD"))
    public void mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos blockPos, LivingEntity livingEntity, CallbackInfoReturnable<Boolean> cir) {
        TraitRegistry.INSTANCE.perform(itemStack, it -> it.onMine(level, livingEntity, blockState, blockPos, itemStack));
    }

    @Inject(method = "hurtEnemy", at = @At("HEAD"))
    public void hurtEnemy(ItemStack itemStack, LivingEntity attacker, LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        TraitRegistry.INSTANCE.perform(itemStack, it -> it.onHurt(attacker.level(), attacker, target, itemStack));
    }

    @Inject(method = "inventoryTick", at = @At("HEAD"))
    public void onInventory(ItemStack itemStack, Level level, Entity entity, int i, boolean bl, CallbackInfo ci) {
        TraitRegistry.INSTANCE.perform(itemStack, it -> it.onInventoryTick(level, entity, itemStack));
    }

    @Inject(method = "releaseUsing", at = @At("HEAD"))
    public void onRelease(ItemStack itemStack, Level level, LivingEntity livingEntity, int i, CallbackInfo ci) {
        TraitRegistry.INSTANCE.perform(itemStack, it -> it.onRelease(level, livingEntity, itemStack));
    }

    @Inject(method = "finishUsingItem", at = @At("HEAD"))
    public void onConsume(ItemStack itemStack, Level level, LivingEntity livingEntity, CallbackInfoReturnable<ItemStack> cir) {
        TraitRegistry.INSTANCE.perform(itemStack, it -> it.onConsume(level, livingEntity, itemStack));
    }
}