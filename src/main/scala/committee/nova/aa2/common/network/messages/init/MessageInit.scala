package committee.nova.aa2.common.network.messages.init

import committee.nova.aa2.AA2
import committee.nova.aa2.common.network.messages.impl.{ReloadClientMessage, ReloadClientMessageHandler, ReloadMessage, ReloadMessageHandler}
import cpw.mods.fml.relauncher.Side

object MessageInit {
  def init(): Unit = {
    val network = AA2.network
    network.registerMessage(classOf[ReloadMessageHandler], classOf[ReloadMessage], 0, Side.SERVER)
    network.registerMessage(classOf[ReloadClientMessageHandler], classOf[ReloadClientMessage], 1, Side.CLIENT)
  }
}
