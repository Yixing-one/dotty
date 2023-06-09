// scalajs: --skip

object Test {
  def main(args: Array[String]): Unit = {
    // these should give unboxed results
    println(().getClass)
    println(().getClass[Unit])
    println(5.getClass)
    println(5.getClass[Int])
    // these should give boxed results
    println(().asInstanceOf[AnyRef with Unit].getClass)
    println(().asInstanceOf[AnyRef with Unit].getClass[Any])
    println(().asInstanceOf[Unit with AnyRef].getClass)
    println(().asInstanceOf[Unit with AnyRef].getClass[Any])
    println(5.asInstanceOf[AnyRef with Int].getClass)
    println(5.asInstanceOf[AnyRef with Int].getClass[Any])
    println(5.asInstanceOf[Int with AnyRef].getClass)
    println(5.asInstanceOf[Int with AnyRef].getClass[Any])
    //make sure ## wasn't broken
    println(5.##)
    println((5.asInstanceOf[AnyRef]).##)
    println((5:Any).##)
  }
}
