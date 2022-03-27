package committee.nova.aa2.client.render.overlay.util

import net.minecraft.client.renderer.OpenGlHelper
import org.lwjgl.opengl.GL11

object OverlayRenderUtils {
  def startRender(): Unit = {
    GL11.glDisable(GL11.GL_DEPTH_TEST)
    GL11.glDepthMask(false)
    OpenGlHelper.glBlendFunc(770, 771, 1, 0)
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
    GL11.glDisable(GL11.GL_ALPHA_TEST)
  }

  def endRender(): Unit = {
    GL11.glDepthMask(true)
    GL11.glEnable(GL11.GL_DEPTH_TEST)
    GL11.glEnable(GL11.GL_ALPHA_TEST)
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
  }


}
