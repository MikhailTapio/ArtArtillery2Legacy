package com.example.examplemod

import cpw.mods.fml.common.Mod

object AA2 {
  final val VERSION = "1.0.0"
  final val MODID = "aa2"
  @Mod.Instance(AA2.MODID)
  var instance: AA2 = _
}

@Mod(modid = AA2.MODID, version = AA2.VERSION)
class AA2 {
  AA2.instance = this
}