package committee.nova.aa2.common.util.player

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.IChatComponent

object PlayerHandler {
  def notifyServerPlayer(player: EntityPlayer, component: IChatComponent): Unit = {
    if (!player.worldObj.isRemote) player.addChatMessage(component)
  }

}
