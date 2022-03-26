package committee.nova.aa2.common.item.api

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack

trait IItemTickable {
  def inventoryTick(stack: ItemStack, player: EntityPlayer): Unit
}
