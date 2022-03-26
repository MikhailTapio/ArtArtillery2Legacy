package committee.nova.aa2.common.item.init

import committee.nova.aa2.common.item.base.ItemPortableLauncher
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item

object ItemInit {
  val names: Array[String] = Array("pl_0", "pl_1", "pl_2")
  val itemList: Map[String, Item] =
    Map(
      names(0) -> new ItemPortableLauncher(names(0), 1),
      names(1) -> new ItemPortableLauncher(names(1), 3),
      names(2) -> new ItemPortableLauncher(names(2), 6).setFull3D()
    )

  def init(): Unit = {
    itemList.foreach(list => register(list._1, list._2))
  }

  private def register(id: String, item: Item): Unit = {
    GameRegistry.registerItem(item, id)
  }
}
