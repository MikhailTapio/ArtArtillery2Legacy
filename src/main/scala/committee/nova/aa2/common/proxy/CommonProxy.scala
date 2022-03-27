package committee.nova.aa2.common.proxy

import committee.nova.aa2.client.render.overlay.init.RenderOverlayInit
import committee.nova.aa2.common.item.recipe.RecipeInit
import committee.nova.aa2.common.util.core.TickHandler
import committee.nova.aa2.common.util.registry.RegistryHandler
import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.common.MinecraftForge

class CommonProxy {

  def preInit(event: FMLPreInitializationEvent): Unit = {
    RegistryHandler.register()
  }

  def init(event: FMLInitializationEvent): Unit = {
    RecipeInit.init()
    FMLCommonHandler.instance().bus().register(new TickHandler)
  }

  def postInit(event: FMLPostInitializationEvent): Unit = {
    MinecraftForge.EVENT_BUS.register(new RenderOverlayInit)
  }
}
