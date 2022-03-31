package committee.nova.aa2.common.entity.impl

import committee.nova.aa2.common.config.CommonConfig
import net.minecraft.block.BlockPane
import net.minecraft.block.material.Material
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.projectile.EntityThrowable
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.{DamageSource, MathHelper, MovingObjectPosition}
import net.minecraft.world.World

class ProjectileShell(worldIn: World) extends EntityThrowable(worldIn) {
  val shotByShell: DamageSource = new DamageSource("shotByShell").setProjectile()
  var quick = false
  var isHeavy = false
  var isHE = false
  var shot = false
  var penetration = 5

  def this(world: World, entity: EntityLivingBase) {
    this(world)
    setSize(0.25F, 0.25F)
    this.setLocationAndAngles(entity.posX, entity.posY + entity.getEyeHeight.toDouble, entity.posZ, entity.rotationYaw, entity.rotationPitch)
    this.posX -= (MathHelper.cos(this.rotationYaw / 180.0F * Math.PI.toFloat) * 0.16F).toDouble
    this.posY -= 0.1D
    this.posZ -= (MathHelper.sin(this.rotationYaw / 180.0F * Math.PI.toFloat) * 0.16F).toDouble
    this.setPosition(this.posX, this.posY, this.posZ)
    this.yOffset = 0.0F
    val f = 0.4F
    this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * Math.PI.toFloat) * MathHelper.cos(this.rotationPitch / 180.0F * Math.PI.toFloat) * f).toDouble
    this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * Math.PI.toFloat) * MathHelper.cos(this.rotationPitch / 180.0F * Math.PI.toFloat) * f).toDouble
    this.motionY = (-MathHelper.sin((this.rotationPitch + this.func_70183_g) / 180.0F * Math.PI.toFloat) * f).toDouble
    this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, 1.5F, 1.0F)
  }

  def this(world: World, entity: EntityLivingBase, isHE: Boolean, isHeavy: Boolean, isQuick: Boolean) {
    this(world, entity)
    this.isHE = isHE
    this.isHeavy = isHeavy
    this.quick = isQuick
    this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, 1.5F * (if (quick) 1.2F else 1F) * (if (isHeavy) 0.8F else 1F), 1.0F)
  }

  override def onImpact(m: MovingObjectPosition): Unit = {
    val hitType = m.typeOfHit
    if (hitType == MovingObjectPosition.MovingObjectType.ENTITY) {
      val entity = m.entityHit
      if (entity == null) return
      if (!shot) {
        shot = true
        return
      }
      if (isHE) {
        explode(m, isHE)
        return
      }
      penetration -= 1
      if (penetration <= 0) {
        explode(m, isHE = false)
        return
      }
      val dmg = CommonConfig.penetrationDmg * (if (isHeavy) 1.2F else 1F)
      entity.attackEntityFrom(shotByShell, dmg)
      speedDecay(entity.width)
      return
    }
    if (hitType == MovingObjectPosition.MovingObjectType.BLOCK) {
      if (isHE) {
        explode(m, isHE)
        return
      }
      val block = worldIn.getBlock(m.blockX, m.blockY, m.blockZ)
      val material = block.getMaterial
      if (material != Material.glass && material != Material.ice) {
        explode(m, isHE = false)
        return
      }
      penetration -= (if (block.isInstanceOf[BlockPane]) 1 else 2)
      if (penetration <= 0) {
        explode(m, isHE = false)
        return
      }
      worldIn.setBlockToAir(m.blockX, m.blockY, m.blockZ)
      worldIn.playSoundEffect(m.blockX, m.blockY, m.blockZ, "dig.glass", 1F, 1F)
    }
  }

  def explode(m: MovingObjectPosition, isHE: Boolean): Unit = {
    val power = if (isHE) CommonConfig.heExplosionPower else CommonConfig.apExplosionPower
    worldIn.newExplosion(this.getThrower, m.hitVec.xCoord, m.hitVec.yCoord, m.hitVec.zCoord, power, isHE && CommonConfig.isHEWithFlame, true)
    this.setDead()
  }

  def speedDecay(width: Float): Unit = {
    val decayAmount = Math.max(0.6F, 1 - Math.sqrt(width / 10))
    this.motionX *= decayAmount
    this.motionY *= decayAmount
    this.motionZ *= decayAmount
  }

  override def getGravityVelocity: Float = 0.01F

  override def readEntityFromNBT(tag: NBTTagCompound): Unit = {
    penetration = tag.getByte("penetration")
    quick = tag.getBoolean("quick")
    isHeavy = tag.getBoolean("heavy")
    isHE = tag.getBoolean("he")
    shot = tag.getBoolean("shot")
    super.readEntityFromNBT(tag)
  }

  override def writeEntityToNBT(tag: NBTTagCompound): Unit = {
    tag.setByte("penetration", penetration.toByte)
    tag.setBoolean("quick", quick)
    tag.setBoolean("heavy", isHeavy)
    tag.setBoolean("he", isHE)
    tag.setBoolean("shot", shot)
    super.writeEntityToNBT(tag)
  }
}
