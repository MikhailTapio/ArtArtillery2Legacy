package committee.nova.aa2

import committee.nova.aa2.AA2.proxy
import committee.nova.aa2.common.proxy.CommonProxy
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.network.NetworkRegistry
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
import cpw.mods.fml.common.{Mod, SidedProxy}

object AA2 {
  final val VERSION = "1.0.0"
  final val NAME = "Art?Artillery!II"
  final val MODID = "aa2"
  val network: SimpleNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("aa2")
  @SidedProxy(clientSide = "committee.nova.aa2.client.proxy.ClientProxy", serverSide = "committee.nova.aa2.common.proxy.CommonProxy")
  val proxy: CommonProxy = new CommonProxy
  @Mod.Instance(AA2.MODID)
  var instance: AA2 = _
}

@Mod(modid = AA2.MODID, name = AA2.NAME, version = AA2.VERSION)
class AA2 {
  AA2.instance = this

  @EventHandler
  def preInit(event: FMLPreInitializationEvent): Unit = proxy.preInit(event)

  @EventHandler
  def init(event: FMLInitializationEvent): Unit = proxy.init(event)

  @EventHandler
  def postInit(event: FMLPostInitializationEvent): Unit = proxy.postInit(event)

}