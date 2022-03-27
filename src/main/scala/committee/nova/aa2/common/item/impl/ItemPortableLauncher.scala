package committee.nova.aa2.common.item.impl

import committee.nova.aa2.common.entity.impl.ProjectileShell
import committee.nova.aa2.common.item.api.{IAmmunitionRenderable, IItemTickable}
import committee.nova.aa2.common.item.base.ItemMeleeless
import committee.nova.aa2.common.item.base.NBTReference._
import committee.nova.aa2.common.item.init.ItemInit
import committee.nova.aa2.common.util.misc.ItemStackUtils
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World

class ItemPortableLauncher(id: String, magazine: Int) extends ItemMeleeless(id) with IItemTickable with IAmmunitionRenderable {
  this.setTextureName("aa2:weapon/" + id)
  this.setMaxDamage(magazine * 300)

  override def isFull3D: Boolean = true

  override def getItemEnchantability: Int = 20

  //todo:a
  override def onItemRightClick(stack: ItemStack, world: World, player: EntityPlayer): ItemStack = {
    reload(player, stack)
    stack
  }

  def reload(player: EntityPlayer, stack: ItemStack): Unit = {
    val nbt = ItemStackUtils.getOrCreateTag(stack)
    if (nbt == null) return
    if (nbt.getInteger(RCD) > 0) {
      player.playSound("random.click", 0.5F, 1F)
      return
    }
    val c = nbt.getInteger(C_MAGAZINE)
    if (c >= nbt.getInteger(M_MAGAZINE)) {
      player.playSound("random.click", 0.5F, 1F)
      return
    }
    val inventory = player.inventory
    val shell = ItemInit.itemList(ItemInit.names(3))
    val hasShell = inventory.hasItem(shell)
    if (!hasShell) {
      player.playSound("random.click", 0.5F, 1F)
      return
    }
    inventory.consumeInventoryItem(shell)
    nbt.setInteger(C_MAGAZINE, c + 1)
    nbt.setInteger(RCD, 60)
  }

  override def onEntitySwing(entityLiving: EntityLivingBase, stack: ItemStack): Boolean = {
    launch(entityLiving, stack)
    true
  }

  def launch(entityLiving: EntityLivingBase, stack: ItemStack): Unit = {
    val world = entityLiving.worldObj
    val nbt = ItemStackUtils.getOrCreateTag(stack)
    if (!checkAvailable(entityLiving, stack, nbt)) return
    //todo:c
    val shell = new ProjectileShell(world, entityLiving)
    if (!world.isRemote) world.spawnEntityInWorld(shell)
    nbt.setInteger(FCD, 60)
    stack.damageItem(1, entityLiving)
  }

  def checkAvailable(player: EntityLivingBase, stack: ItemStack, nbt: NBTTagCompound): Boolean = {
    if (nbt == null) return false
    if (nbt.getInteger(FCD) > 0) {
      player.playSound("random.fizz", 0.5F, 1F)
      return false
    }
    consume(stack)
  }

  def consume(stack: ItemStack): Boolean = {
    val nbt = ItemStackUtils.getOrCreateTag(stack)
    val c = nbt.getInteger(C_MAGAZINE) - 1
    if (c < 0) return false
    nbt.setInteger(C_MAGAZINE, c)
    true
  }

  override def onCreated(stack: ItemStack, world: World, player: EntityPlayer): Unit = initializeNbt(stack)

  override def inventoryTick(stack: ItemStack, player: EntityPlayer): Unit = {
    initializeNbt(stack)
    decrementCD(stack)
  }

  def initializeNbt(stack: ItemStack): Unit = {
    val nbt = ItemStackUtils.getOrCreateTag(stack)
    if (nbt.hasKey(M_MAGAZINE)) return
    nbt.setInteger(C_MAGAZINE, 0)
    nbt.setInteger(M_MAGAZINE, magazine)
    nbt.setInteger(RCD, 0)
    nbt.setInteger(FCD, 0)
  }

  def decrementCD(stack: ItemStack): Unit = {
    val nbt = ItemStackUtils.getOrCreateTag(stack)
    val fcd = nbt.getInteger(FCD)
    val rcd = nbt.getInteger(RCD)
    if (fcd > 0) nbt.setInteger(FCD, fcd - 1)
    if (rcd > 0) nbt.setInteger(RCD, rcd - 1)
  }
}
