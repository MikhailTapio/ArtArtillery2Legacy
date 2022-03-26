package committee.nova.aa2.common.util.misc

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

object ItemStackUtils {
  def getOrCreateTag(stack: ItemStack): NBTTagCompound = {
    if (stack.stackTagCompound == null) stack.setTagCompound(new NBTTagCompound)
    stack.stackTagCompound
  }
}
