package committee.nova.aa2.common.item.enchantment.base

import committee.nova.aa2.common.item.api.IReloadable
import committee.nova.aa2.common.util.misc.FormatUtils
import net.minecraft.enchantment.{Enchantment, EnumEnchantmentType}
import net.minecraft.item.ItemStack

class EnchantmentBase(name: String, id: Int, maxLevel: Int) extends Enchantment(id, 5, EnumEnchantmentType.all) {
  setName(FormatUtils.getRawName(name))

  override def getMaxLevel: Int = maxLevel

  override def canApply(stack: ItemStack): Boolean = stack.getItem.isInstanceOf[IReloadable]
}
