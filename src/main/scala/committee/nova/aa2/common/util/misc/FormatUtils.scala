package committee.nova.aa2.common.util.misc

import committee.nova.aa2.AA2

object FormatUtils {
  def getRawName(id: String): String = AA2.MODID + "." + id
}
