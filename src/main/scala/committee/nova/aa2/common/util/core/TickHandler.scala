package committee.nova.aa2.common.util.core

import committee.nova.aa2.common.item.api.IItemTickable
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.TickEvent

class TickHandler {
  @SubscribeEvent
  def onItemTick(event: TickEvent.PlayerTickEvent): Unit = {
    val player = event.player
    val inventory = player.inventory.mainInventory
    for (stack <- inventory) {
      if (stack != null) {
        stack.getItem match {
          case tickable: IItemTickable => tickable.inventoryTick(stack, player)
          case _ =>
        }
      }
    }
  }
}
