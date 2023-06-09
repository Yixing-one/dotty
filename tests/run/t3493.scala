// scalajs: --skip

object Test {

  def main(args: Array[String]): Unit = {
    import scala.collection.immutable.*
    val x = TreeSet("a", "b", "c", "d")
    val x2 = x + "e"
    assert(x2.toString == "TreeSet(a, b, c, d, e)")
    assert(x2.toString == runtime.ScalaRunTime.stringOf(x2).trim)
  }

}
