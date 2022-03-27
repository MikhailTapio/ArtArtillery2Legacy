package committee.nova.aa2.common.item.api

import committee.nova.aa2.common.item.base.NBTReference
import committee.nova.aa2.common.util.misc.ItemStackUtils
import net.minecraft.item.ItemStack

trait IAmmunitionRenderable {
  def getCurrentMagazine(stack: ItemStack): Int = {
    val nbt = ItemStackUtils.getOrCreateTag(stack)
    nbt.getInteger(NBTReference.C_MAGAZINE)
  }

  def getReloadTimeLeft(stack: ItemStack): Float = {
    val nbt = ItemStackUtils.getOrCreateTag(stack)
    nbt.getInteger(NBTReference.RCD) * 1F / 20F
  }

  def getCoolDownTimeLeft(stack: ItemStack): Float = {
    val nbt = ItemStackUtils.getOrCreateTag(stack)
    nbt.getInteger(NBTReference.FCD) * 1F / 20F
  }
}
