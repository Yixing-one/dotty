object NothingMethod {
  def nothingMethod(): Nothing =
    throw new Exception("boom")
}

class Foo(val str: String) {
  def this(arr: Array[Char]) = this({
    if (arr.length == 0) NothingMethod.nothingMethod()
    new String(arr)
  })
}

object Test {
  def main(args: Array[String]) = {
    val t = new Foo(Array('a', 'b', 'c'))
    println(t.str)
  }
}
