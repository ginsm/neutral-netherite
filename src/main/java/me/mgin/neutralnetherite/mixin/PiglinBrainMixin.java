package me.mgin.neutralnetherite.mixin;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Mixin(PiglinBrain.class)
public abstract class PiglinBrainMixin {

  @Inject(method = "wearsGoldArmor", at = @At("RETURN"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
  private static void wearsGoldArmor(LivingEntity entity, @NotNull CallbackInfoReturnable<Boolean> cir){
    Iterable<ItemStack> iterable = entity.getArmorItems();

    for (ItemStack itemStack : iterable) {
      Item item = itemStack.getItem();

      if (!(item instanceof ArmorItem) || ((ArmorItem) item).getMaterial() != ArmorMaterials.GOLD && ((ArmorItem) item).getMaterial() != ArmorMaterials.NETHERITE)
        continue;

      cir.setReturnValue(true);
      return;
    }

    cir.setReturnValue(false);
  }
}
