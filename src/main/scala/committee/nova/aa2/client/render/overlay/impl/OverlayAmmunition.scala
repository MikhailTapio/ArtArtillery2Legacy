package committee.nova.aa2.client.render.overlay.impl

import com.ibm.icu.text.MessageFormat
import committee.nova.aa2.client.render.overlay.util.{MappedTexture, OverlayRenderUtils}
import committee.nova.aa2.common.item.api.IReloadable
import committee.nova.aa2.common.util.misc.FormatUtils
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.{Gui, ScaledResolution}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ChatComponentTranslation

class OverlayAmmunition() extends Gui {
  def this(mc: Minecraft, player: EntityPlayer) {
    this()
    initialize(mc, player)
  }

  def initialize(mc: Minecraft, player: EntityPlayer): Unit = {
    val stack: ItemStack = player.getHeldItem
    if (stack == null) return
    val item = stack.getItem
    item match {
      case _: IReloadable =>
        val resolutionScaled = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight)
        val y: Int = resolutionScaled.getScaledHeight
        val renderable = item.asInstanceOf[IReloadable]
        val amount = renderable.getCurrentMagazine(stack)
        val show = amount > 0
        val mappedTexture = new MappedTexture(if (show) 1 else 0)
        OverlayRenderUtils.startRender()
        mc.getTextureManager.bindTexture(mappedTexture.getTexture)
        val uV = mappedTexture.getUv
        drawTexturedModalRect(3, y - 18, uV(0), uV(1), 16, 16)
        OverlayRenderUtils.endRender()
        drawString(mc.fontRenderer, if (show) String.valueOf(amount) else "", 15, y - 12, Integer.parseInt("FFFFFF", 16))
        val reloadTime = renderable.getReloadTimeLeft(stack)
        if (reloadTime > 0F) {
          val x: Int = resolutionScaled.getScaledWidth
          mc.fontRenderer.drawString(MessageFormat.format(
            new ChatComponentTranslation("overlay.aa2.ammunition").getFormattedText, FormatUtils.getFormattedNumber(reloadTime, 1),
            new ChatComponentTranslation("unit.aa2.second").getFormattedText), x / 2 + 16, y / 2 + 5, Integer.parseInt("DD0000", 16))
        }
        val cdTime = renderable.getCoolDownTimeLeft(stack)
        if (cdTime > 0F) {
          val x: Int = resolutionScaled.getScaledWidth
          mc.fontRenderer.drawString(MessageFormat.format(
            new ChatComponentTranslation("overlay.aa2.cd").getFormattedText, FormatUtils.getFormattedNumber(cdTime, 1),
            new ChatComponentTranslation("unit.aa2.second").getFormattedText), x / 2 + 16, y / 2 - 8, Integer.parseInt("AA00FF", 16))
        }
      case _ =>
    }
  }

}
