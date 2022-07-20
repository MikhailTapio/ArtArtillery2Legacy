package committee.nova.aa2.client.proxy

import committee.nova.aa2.client.render.overlay.init.RenderOverlayInit
import committee.nova.aa2.common.proxy.CommonProxy
import committee.nova.aa2.common.util.registry.RegistryHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.common.MinecraftForge

class ClientProxy extends CommonProxy {
  override def preInit(event: FMLPreInitializationEvent): Unit = {
    super.preInit(event)
    RegistryHandler.registerClient()
  }

  override def init(event: FMLInitializationEvent): Unit = {
    super.init(event)
  }

  override def postInit(event: FMLPostInitializationEvent): Unit = {
    super.postInit(event)
    MinecraftForge.EVENT_BUS.register(new RenderOverlayInit)
  }
}
