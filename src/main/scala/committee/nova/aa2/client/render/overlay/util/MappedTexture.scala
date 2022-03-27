package committee.nova.aa2.client.render.overlay.util

import committee.nova.aa2.AA2
import net.minecraft.util.ResourceLocation

class MappedTexture(id: Int) {
  /*
  0:NO_SHELL
  1:SHELL
   */
  val overlayElements = new ResourceLocation(AA2.MODID, "textures/overlay/overlay_elements.png")
  val uvMap = Map(0 -> Array(1, 1), 1 -> Array(18, 1))
  val textureMap = Map(0 -> overlayElements, 1 -> overlayElements)

  def getUv: Array[Int] = uvMap(id)

  def getTexture: ResourceLocation = textureMap(id)
}
