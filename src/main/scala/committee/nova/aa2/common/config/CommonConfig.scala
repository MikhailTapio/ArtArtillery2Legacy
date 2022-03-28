package committee.nova.aa2.common.config

import committee.nova.aa2.common.config.CommonConfig._
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.common.config.Configuration
import org.apache.logging.log4j.Logger


object CommonConfig {
  var penetrationDmg: Float = _
  var apExplosionPower: Float = _
  var heExplosionPower: Float = _
  var isHEWithFlame: Boolean = _
  var baseReloadTime: Int = _
  var baseCoolDownTime: Int = _
  var enchantmentSHSId: Int = _
  var enchantmentEnhancedCaseId: Int = _
  var enchantmentQuickReloadId: Int = _
  var enchantmentQuickCoolingId: Int = _
  var enchantmentGrandetId: Int = _
  var enchantmentHESId: Int = _
  private var config: Configuration = _
  private var logger: Logger = _

  def getLogger: Logger = logger
}

class CommonConfig(event: FMLPreInitializationEvent) {
  val enchantmentMin = 64
  val enchantmentMax = 255
  logger = event.getModLog
  config = new Configuration(event.getSuggestedConfigurationFile)
  config.load()
  load()

  def load(): Unit = {
    penetrationDmg = config.getFloat(
      "penetrationDamage", Configuration.CATEGORY_GENERAL, 6F, 1F, 500F, "Damage caused by penetration of an AP shell")
    apExplosionPower = config.getFloat(
      "apShellExplosionPower", Configuration.CATEGORY_GENERAL, 2F, 0F, 50F, "Power of an AP shell's explosion"
    )
    heExplosionPower = config.getFloat(
      "heShellExplosionPower", Configuration.CATEGORY_GENERAL, 4F, 0F, 80F, "Power of an HE shell's explosion"
    )
    isHEWithFlame = config.getBoolean(
      "isHEWithFlame", Configuration.CATEGORY_GENERAL, true, "Should an HE shell explodes with flame?"
    )
    baseReloadTime = config.getInt(
      "baseReloadTime", Configuration.CATEGORY_GENERAL, 60, 2, 600, "Base reload time of a portable launcher (Default is 60ticks = 3 sec)"
    )
    baseCoolDownTime = config.getInt(
      "baseCoolDownTime", Configuration.CATEGORY_GENERAL, 60, 2, 600, "Base cool-down time of a portable launcher (Default is 60ticks = 3 sec)"
    )
    enchantmentSHSId = getEnchantmentId("SuperHeavyShell", 134)
    enchantmentQuickReloadId = getEnchantmentId("QuickReload", 135)
    enchantmentQuickCoolingId = getEnchantmentId("QuickCooling", 136)
    enchantmentEnhancedCaseId = getEnchantmentId("EnhancedCase", 137)
    enchantmentGrandetId = getEnchantmentId("Grandet", 138)
    enchantmentHESId = getEnchantmentId("HighExplosiveShell", 139)
    config.save()
  }

  def getEnchantmentId(name: String, default: Int): Int = config.getInt(name, Configuration.CATEGORY_GENERAL, default, enchantmentMin, enchantmentMax, "Id of the enchantment " + name)
}
