package committee.nova.aa2.common.proxy

import committee.nova.aa2.common.util.core.TickHandler
import committee.nova.aa2.common.util.registry.RegistryHandler
import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}

class CommonProxy {

  def preInit(event: FMLPreInitializationEvent): Unit = {
    RegistryHandler.register()
  }

  def init(event: FMLInitializationEvent): Unit = {
    FMLCommonHandler.instance().bus().register(new TickHandler)
  }

  def postInit(event: FMLPostInitializationEvent): Unit = {

  }
}
