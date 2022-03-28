package committee.nova.aa2.common.item.impl

import committee.nova.aa2.common.config.CommonConfig
import committee.nova.aa2.common.entity.impl.ProjectileShell
import committee.nova.aa2.common.item.api.{IItemTickable, IReloadable}
import committee.nova.aa2.common.item.base.ItemMeleeless
import committee.nova.aa2.common.item.base.NBTReference._
import committee.nova.aa2.common.item.enchantment.init.EnchantmentInit
import committee.nova.aa2.common.item.init.ItemInit
import committee.nova.aa2.common.util.misc.ItemStackUtils
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World

import scala.util.Random

class ItemPortableLauncher(id: String, magazine: Int) extends ItemMeleeless(id) with IItemTickable with IReloadable {
  this.setTextureName("aa2:weapon/" + id)
  this.setMaxDamage(magazine * 300)
  this.setMaxStackSize(1)

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
    player.playSound("aa2:launcher.reload", 1F, 1F)
    inventory.consumeInventoryItem(shell)
    nbt.setInteger(C_MAGAZINE, c + 1)
    val effect = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.enchantmentMap(EnchantmentInit.quickReload).effectId, stack)
    val rcd = ItemStackUtils.getCdTime(effect, CommonConfig.baseReloadTime)
    nbt.setInteger(RCD, rcd)
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
    val heavy = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.enchantmentMap(EnchantmentInit.shs).effectId, stack) > 0
    val he = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.enchantmentMap(EnchantmentInit.hes).effectId, stack) > 0
    val quick = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.enchantmentMap(EnchantmentInit.enhancedCase).effectId, stack) > 0
    val shell = new ProjectileShell(world, entityLiving, he, heavy, quick)
    entityLiving.playSound("aa2:launcher.launch", 1F, 1F)
    if (!world.isRemote) world.spawnEntityInWorld(shell)
    val effect = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.enchantmentMap(EnchantmentInit.quickCooling).effectId, stack)
    val fcd = ItemStackUtils.getCdTime(effect, CommonConfig.baseCoolDownTime)
    nbt.setInteger(FCD, fcd)
    stack.damageItem(1, entityLiving)
    val rand = entityLiving.worldObj.rand
    entityLiving.rotationPitch -= (3F + rand.nextFloat())
    entityLiving.rotationYawHead += (-2F + 4 * rand.nextFloat())
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
    val c = nbt.getInteger(C_MAGAZINE)
    if (c < 1) return false
    val grandeted = new Random().nextDouble() > (2 - Math.pow(1.1, EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.enchantmentMap(EnchantmentInit.grandet).effectId, stack)))
    nbt.setInteger(C_MAGAZINE, if (grandeted) c else c - 1)
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
