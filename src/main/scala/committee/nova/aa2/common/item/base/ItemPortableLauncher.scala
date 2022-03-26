package committee.nova.aa2.common.item.base

import committee.nova.aa2.common.item.api.ItemMeleeless
import committee.nova.aa2.common.util.misc.FormatUtils
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class ItemPortableLauncher(id: String, magazine: Int) extends ItemMeleeless {
  this.setUnlocalizedName(FormatUtils.getRawName(id))
  this.setTextureName("aa2:weapon/" + id)
  this.setMaxDamage(magazine * 300)

  override def isFull3D: Boolean = true

  override def getItemEnchantability: Int = 20

  //todo:a
  override def onItemRightClick(stack: ItemStack, world: World, player: EntityPlayer): ItemStack = {
    if (world.isRemote) return stack
    reload(player, stack)
    stack
  }

  def reload(player: EntityPlayer, stack: ItemStack): Unit = println("0")

  override def onEntitySwing(entityLiving: EntityLivingBase, stack: ItemStack): Boolean = {
    launch(entityLiving, stack)
    true
  }

  def launch(entityLiving: EntityLivingBase, stack: ItemStack): Unit = {
    val world = entityLiving.worldObj
    if (world.isRemote) return
    println(1)
  }
}
