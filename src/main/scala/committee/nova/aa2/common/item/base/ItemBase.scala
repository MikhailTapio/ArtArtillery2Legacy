package committee.nova.aa2.common.item.base

import committee.nova.aa2.AA2
import committee.nova.aa2.common.util.misc.FormatUtils
import net.minecraft.item.Item

class ItemBase(id: String) extends Item {
  this.setUnlocalizedName(FormatUtils.getRawName(id))
  this.setCreativeTab(AA2.tabAA2)
}
