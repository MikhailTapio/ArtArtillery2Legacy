package committee.nova.aa2.common.item.enchantment.init

import committee.nova.aa2.common.config.CommonConfig
import committee.nova.aa2.common.item.enchantment.base.{EnchantmentBase, EnchantmentInfo}
import net.minecraft.enchantment.Enchantment

import java.text.MessageFormat
import scala.collection.mutable

object EnchantmentInit {
  val quickReload = "quickReload"
  val quickCooling = "quickCooling"
  val shs = "superHeavyShell"
  val hes = "highExplosiveShell"
  val enhancedCase = "enhancedCase"
  val grandet = "grandet"
  val enchantmentList: Array[EnchantmentInfo] = Array(
    EnchantmentInfo(quickReload, CommonConfig.enchantmentQuickReloadId, 3), EnchantmentInfo(quickCooling, CommonConfig.enchantmentQuickCoolingId, 3),
    EnchantmentInfo(shs, CommonConfig.enchantmentSHSId, 1), EnchantmentInfo(hes, CommonConfig.enchantmentHESId, 1),
    EnchantmentInfo(enhancedCase, CommonConfig.enchantmentEnhancedCaseId, 1), EnchantmentInfo(grandet, CommonConfig.enchantmentGrandetId, 4)
  )
  val enchantmentMap = new mutable.HashMap[String, Enchantment]()

  def init(): Unit = {
    enchantmentList.foreach(e => addEnchantment(e))
  }

  private def addEnchantment(info: EnchantmentInfo): Unit = {
    try {
      val enchantment = new EnchantmentBase(info.name, info.id, info.maxLevel)
      enchantmentMap.put(info.name, enchantment)
      Enchantment.addToBookList(enchantment)
    } catch {
      case _: Exception => CommonConfig.getLogger.error(MessageFormat.format("Duplicate or illegal enchantment id: {0}, registry of enchantment {1} will be skipped.", String.valueOf(info.id), info.name))
    }
  }

}
