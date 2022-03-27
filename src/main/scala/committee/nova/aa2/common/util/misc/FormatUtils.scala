package committee.nova.aa2.common.util.misc

import committee.nova.aa2.AA2

import java.text.MessageFormat

object FormatUtils {
  def getRawName(id: String): String = AA2.MODID + "." + id

  def getFormattedNumber(raw: Float, amount: Int): String = raw.formatted(MessageFormat.format("%.{0}f", String.valueOf(amount)))
}
