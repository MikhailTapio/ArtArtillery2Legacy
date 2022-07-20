package committee.nova.aa2.common.proxy

import committee.nova.aa2.common.config.CommonConfig
import committee.nova.aa2.common.item.enchantment.init.EnchantmentInit
import committee.nova.aa2.common.item.recipe.RecipeInit
import committee.nova.aa2.common.util.core.TickHandler
import committee.nova.aa2.common.util.registry.RegistryHandler
import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}

class CommonProxy {

  def preInit(event: FMLPreInitializationEvent): Unit = {
    new CommonConfig(event)
    RegistryHandler.registerCommon()
  }

  def init(event: FMLInitializationEvent): Unit = {
    RecipeInit.init()
    EnchantmentInit.init()
    FMLCommonHandler.instance().bus().register(new TickHandler)
  }

  def postInit(event: FMLPostInitializationEvent): Unit = {}
}
