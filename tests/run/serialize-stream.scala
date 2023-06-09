// scalajs: --skip

object Test {
  def ser[T](s: Seq[T]): Unit = {
    val bos = new java.io.ByteArrayOutputStream()
    val oos = new java.io.ObjectOutputStream(bos)
    oos.writeObject(s)

    val ois = new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(bos.toByteArray))
    val obj = ois.readObject()
    println(obj)
    println(obj.asInstanceOf[Seq[T]].toList)
  }

  def main(args: Array[String]): Unit = {
    ser(Stream(1, 2, 3))
    ser(Stream(1))
    ser(Stream())

    ser(LazyList(1, 2, 3))
    ser(LazyList(1))
    ser(LazyList())
  }
}
