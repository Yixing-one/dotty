// scalajs: --skip

import runtime.ArrayCharSequence

object Test {
  val arr = Array[Char]('a' to 'i'*)
  var xs: CharSequence = new runtime.ArrayCharSequence(arr, 0, arr.length)
  val hash = xs.hashCode

  def check(chars: CharSequence): Unit = {
    println("\n[check '" + chars + "'] len = " + chars.length)
    chars match {
      case x: runtime.ArrayCharSequence => assert(x.xs eq arr, ((x.xs, arr)))
      case x                            => assert(false, x)
    }

    0 until chars.length foreach { i =>
      println("sub(%s, %s) == '%s'".format(i, chars.length, chars.subSequence(i, chars.length)))
      println("sub(%s, %s) == '%s'".format(0, i, chars.subSequence(0, i)))
    }
    if (chars.length >= 2)
      check(chars.subSequence(1, chars.length - 1))
  }

  def main(args: Array[String]): Unit = {
    while (xs.length > 0) {
      check(xs)
      xs = xs.subSequence(0, xs.length - 1)
    }
  }
}
