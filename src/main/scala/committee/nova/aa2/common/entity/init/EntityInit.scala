package committee.nova.aa2.common.entity.init

import committee.nova.aa2.AA2
import committee.nova.aa2.client.render.entity.RenderShell
import committee.nova.aa2.common.entity.impl.ProjectileShell
import cpw.mods.fml.client.registry.RenderingRegistry
import cpw.mods.fml.common.registry.EntityRegistry
import net.minecraft.client.renderer.entity.Render
import net.minecraft.entity.Entity

object EntityInit {
  private var nextID = 0

  def init(): Unit = {
    registerEntity(classOf[ProjectileShell], "GenericShell", 96, 10, sendsVUpdates = true)
    registerEntityRenderer(classOf[ProjectileShell], new RenderShell)
  }

  private def registerEntity(entityClass: Class[_ <: Entity], name: String, trackingRange: Int, updateFreq: Int, sendsVUpdates: Boolean): Unit = {
    nextID += 1
    EntityRegistry.registerModEntity(entityClass, name, nextID, AA2.instance, trackingRange, updateFreq, sendsVUpdates)
  }

  private def registerEntityRenderer(entityClass: Class[_ <: Entity], render: Render): Unit = RenderingRegistry.registerEntityRenderingHandler(entityClass, render)
}
