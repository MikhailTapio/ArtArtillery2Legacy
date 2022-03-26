package committee.nova.aa2.common.util.registry

import committee.nova.aa2.common.item.init.ItemInit
import committee.nova.aa2.common.network.messages.init.MessageInit


object RegistryHandler {
  def register(): Unit = {
    ItemInit.init()
    MessageInit.init()
  }
}
