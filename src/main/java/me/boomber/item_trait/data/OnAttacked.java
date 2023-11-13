package me.boomber.item_trait.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.boomber.item_trait.trait.Trait;
import me.boomber.item_trait.utils.CommandCallback;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@EqualsAndHashCode(callSuper = true)
@Data
public class OnAttacked extends Trait {
    final CommandCallback attackerCommand;
    final CommandCallback targetCommand;

    @Override
    public void onHurt(Level level, LivingEntity attacker, LivingEntity target, ItemStack itemStack) {
        attackerCommand.execute(attacker);
        targetCommand.execute(target);
    }
}
