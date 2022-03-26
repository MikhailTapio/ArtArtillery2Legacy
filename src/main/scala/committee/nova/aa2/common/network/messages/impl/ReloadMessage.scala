package committee.nova.aa2.common.network.messages.impl

import cpw.mods.fml.common.network.simpleimpl.{IMessage, IMessageHandler, MessageContext}
import io.netty.buffer.ByteBuf

import scala.util.Random

class ReloadMessage extends IMessage {
  private var value = new Random().nextInt()

  override def fromBytes(buf: ByteBuf): Unit = this.value = buf.readInt()

  override def toBytes(buf: ByteBuf): Unit = buf.writeInt(this.value)


}

class ReloadMessageHandler extends IMessageHandler[ReloadMessage, IMessage] {
  override def onMessage(message: ReloadMessage, ctx: MessageContext): IMessage = {
    //val player = ctx.getServerHandler.playerEntity
    //val stack = player.getHeldItem
    //if (!(stack.getItem == ItemInit.itemList("pfl"))) return _
    //val world = player.worldObj
    //if (world.isRemote || stack == null) return _
    null
  }
}
