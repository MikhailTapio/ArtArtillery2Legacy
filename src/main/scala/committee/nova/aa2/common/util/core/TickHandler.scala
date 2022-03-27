package committee.nova.aa2.common.util.core

import committee.nova.aa2.common.item.api.IItemTickable
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.TickEvent
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack

class TickHandler {
  @SubscribeEvent
  def onItemTick(event: TickEvent.PlayerTickEvent): Unit = {
    val player = event.player
    val inventory = player.inventory.mainInventory
    for (stack <- inventory) {
      tickItem(stack, player)
    }
  }

  private def tickItem(stack: ItemStack, player: EntityPlayer): Unit = {
    if (stack == null) return
    stack.getItem match {
      case tickable: IItemTickable => tickable.inventoryTick(stack, player)
      case _ =>
    }
  }
}
