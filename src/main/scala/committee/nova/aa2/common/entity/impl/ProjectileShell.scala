package committee.nova.aa2.common.entity.impl

import net.minecraft.block.BlockPane
import net.minecraft.block.material.Material
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.projectile.EntityThrowable
import net.minecraft.util.{DamageSource, MathHelper, MovingObjectPosition}
import net.minecraft.world.World

class ProjectileShell(worldIn: World) extends EntityThrowable(worldIn) {
  val shotByShell: DamageSource = new DamageSource("shotByShell").setProjectile()
  var shot = false
  var penetration = 5

  def this(world: World, entity: EntityLivingBase) {
    this(world)
    setSize(0.25F, 0.25F)
    this.setLocationAndAngles(entity.posX, entity.posY + entity.getEyeHeight.toDouble, entity.posZ, entity.rotationYaw, entity.rotationPitch)
    this.posX -= (MathHelper.cos(this.rotationYaw / 180.0F * Math.PI.toFloat) * 0.16F).toDouble
    this.posY -= 0.10000000149011612D
    this.posZ -= (MathHelper.sin(this.rotationYaw / 180.0F * Math.PI.toFloat) * 0.16F).toDouble
    this.setPosition(this.posX, this.posY, this.posZ)
    this.yOffset = 0.0F
    val f = 0.4F
    this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * Math.PI.toFloat) * MathHelper.cos(this.rotationPitch / 180.0F * Math.PI.toFloat) * f).toDouble
    this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * Math.PI.toFloat) * MathHelper.cos(this.rotationPitch / 180.0F * Math.PI.toFloat) * f).toDouble
    this.motionY = (-MathHelper.sin((this.rotationPitch + this.func_70183_g) / 180.0F * Math.PI.toFloat) * f).toDouble
    this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, this.func_70182_d, 1.0F)
  }

  override def onImpact(m: MovingObjectPosition): Unit = {
    val hitType = m.typeOfHit
    if (hitType == MovingObjectPosition.MovingObjectType.ENTITY) {
      val entity = m.entityHit
      //todo:c
      if (entity == null) return
      if (!shot) {
        shot = true
        return
      }
      penetration -= 1
      if (penetration <= 0) {
        explode(m)
        return
      }
      entity.attackEntityFrom(shotByShell, 5F)
      speedDecay(entity.width)
      return
    }
    if (hitType == MovingObjectPosition.MovingObjectType.BLOCK) {
      val block = worldIn.getBlock(m.blockX, m.blockY, m.blockZ)
      val material = block.getMaterial
      if (material != Material.glass && material != Material.ice) {
        explode(m)
        return
      }
      penetration -= (if (block.isInstanceOf[BlockPane]) 1 else 2)
      if (penetration <= 0) {
        explode(m)
        return
      }
      worldIn.setBlockToAir(m.blockX, m.blockY, m.blockZ)
      worldIn.playSoundEffect(m.blockX, m.blockY, m.blockZ, "dig.glass", 1F, 1F)
    }
  }

  def speedDecay(width: Float): Unit = {
    val decayAmount = Math.max(0.6F, 1 - Math.sqrt(width / 10))
    this.motionX *= decayAmount
    this.motionY *= decayAmount
    this.motionZ *= decayAmount
  }

  def explode(m: MovingObjectPosition): Unit = {
    worldIn.createExplosion(this.getThrower, m.hitVec.xCoord, m.hitVec.yCoord, m.hitVec.zCoord, 2F, true)
    this.setDead()
  }

}
