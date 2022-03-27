package committee.nova.aa2.client.render.overlay.impl

import committee.nova.aa2.client.render.overlay.util.{MappedTexture, OverlayRenderUtils}
import committee.nova.aa2.common.item.api.IAmmunitionRenderable
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.{Gui, ScaledResolution}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack

class OverlayAmmunition() extends Gui {
  def this(mc: Minecraft, player: EntityPlayer) {
    this()
    val stack: ItemStack = player.getHeldItem
    val item = stack.getItem
    item match {
      case _: IAmmunitionRenderable =>
        val resolutionScaled = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight)
        val y: Int = resolutionScaled.getScaledHeight
        val amount = item.asInstanceOf[IAmmunitionRenderable].getCurrentMagazine(stack)
        val show = amount > 0
        val mappedTexture = new MappedTexture(if (show) 1 else 0)
        OverlayRenderUtils.startRender()
        mc.getTextureManager.bindTexture(mappedTexture.getTexture)
        val uV = mappedTexture.getUv
        drawTexturedModalRect(3, y - 18, uV(0), uV(1), 16, 16)
        OverlayRenderUtils.endRender()
        drawString(mc.fontRenderer, if (show) String.valueOf(amount) else "", 15, y - 12, Integer.parseInt("FFFFFF", 16))
      case _ =>
    }
  }
}
