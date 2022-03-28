package committee.nova.aa2.common.util.misc

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

object ItemStackUtils {
  def getOrCreateTag(stack: ItemStack): NBTTagCompound = {
    if (stack.stackTagCompound == null) stack.setTagCompound(new NBTTagCompound)
    stack.stackTagCompound
  }

  def getCdTime(effect: Int, base: Int): Int = Math.max(2, (Math.pow(0.8, effect) * base).toInt)
}
