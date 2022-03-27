package committee.nova.aa2.client.render.overlay.init

import committee.nova.aa2.client.render.overlay.impl.OverlayAmmunition
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType

class RenderOverlayInit {
  @SubscribeEvent
  def onRenderGameOverlay(event: RenderGameOverlayEvent.Post): Unit = {
    val mc = Minecraft.getMinecraft
    if (mc == null) return
    val player = mc.thePlayer
    if (player == null) return
    if (event.`type` == ElementType.ALL) new OverlayAmmunition(mc, player)
  }
}
