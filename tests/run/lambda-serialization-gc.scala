// scalajs: --skip

import java.io.*

import java.net.URLClassLoader

class C {
  def serializeDeserialize[T <: AnyRef](obj: T) = {
    val buffer = new ByteArrayOutputStream
    val out = new ObjectOutputStream(buffer)
    out.writeObject(obj)
    val in = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray))
    in.readObject.asInstanceOf[T]
  }

  serializeDeserialize((c: String) => c.length)
}

object Test {
  def main(args: Array[String]): Unit = {
    test()
  }

  def test(): Unit = {
    val loader = getClass.getClassLoader.asInstanceOf[URLClassLoader]
    val loaderCClass = classOf[C]
    def deserializedInThrowawayClassloader = {
      val throwawayLoader: java.net.URLClassLoader = new java.net.URLClassLoader(loader.getURLs, ClassLoader.getSystemClassLoader) {
        val maxMemory = Runtime.getRuntime.maxMemory()
        val junk = new Array[Long]((maxMemory / 8 / 4).toInt)
      }
      val clazz = throwawayLoader.loadClass("C")
      assert(clazz != loaderCClass)
      clazz.newInstance()
    }
    (1 to 5) foreach { i =>
      // This would OOM by the fifth iteration if we leaked `throwawayLoader` during
      // deserialization.
      deserializedInThrowawayClassloader
    }
  }
}
