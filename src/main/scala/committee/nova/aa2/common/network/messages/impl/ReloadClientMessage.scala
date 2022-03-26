package committee.nova.aa2.common.network.messages.impl

import committee.nova.aa2.AA2
import cpw.mods.fml.common.network.simpleimpl.{IMessage, IMessageHandler, MessageContext}
import io.netty.buffer.ByteBuf

class ReloadClientMessage extends IMessage {
  private var value: Int = 0
  private var max: Int = 0

  override def fromBytes(buf: ByteBuf): Unit = {
    this.value = buf.readInt()
    this.max = buf.readInt()
  }

  override def toBytes(buf: ByteBuf): Unit = {
    buf.writeInt(this.value)
    buf.writeInt(this.max)
  }
}

class ReloadClientMessageHandler extends IMessageHandler[ReloadClientMessage, IMessage] {
  override def onMessage(message: ReloadClientMessage, ctx: MessageContext): IMessage = {
    AA2.network.sendToServer(new ReloadMessage)
    null
  }
}
