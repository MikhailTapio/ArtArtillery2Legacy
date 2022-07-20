package committee.nova.aa2.common.util.registry

import committee.nova.aa2.common.entity.init.EntityInit
import committee.nova.aa2.common.item.init.ItemInit
import committee.nova.aa2.common.network.messages.init.MessageInit
import cpw.mods.fml.relauncher.{Side, SideOnly}


object RegistryHandler {
  def registerCommon(): Unit = {
    ItemInit.init()
    EntityInit.initCommon()
    MessageInit.init()
  }

  @SideOnly(Side.CLIENT)
  def registerClient(): Unit = EntityInit.initClient()
}
