package committee.nova.aa2

import committee.nova.aa2.common.item.init.ItemInit
import committee.nova.aa2.common.proxy.CommonProxy
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.network.NetworkRegistry
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
import cpw.mods.fml.common.{Mod, SidedProxy}
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item

@Mod(modid = AA2.MODID, name = AA2.NAME, version = AA2.VERSION, modLanguage = "scala")
object AA2 {
  final val VERSION = "1.0.0pre3"
  final val NAME = "Art?Artillery!II"
  final val MODID = "aa2"
  final val tabAA2 = new CreativeTabs("aa2.main") {
    override def getTabIconItem: Item = ItemInit.itemList(ItemInit.names(2))
  }
  val network: SimpleNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("aa2")
  @SidedProxy(clientSide = "committee.nova.aa2.client.proxy.ClientProxy", serverSide = "committee.nova.aa2.common.proxy.CommonProxy")
  var proxy: CommonProxy = _

  @EventHandler
  def preInit(event: FMLPreInitializationEvent): Unit = proxy.preInit(event)

  @EventHandler
  def init(event: FMLInitializationEvent): Unit = proxy.init(event)

  @EventHandler
  def postInit(event: FMLPostInitializationEvent): Unit = proxy.postInit(event)

}
