package dotty.runtime.vc

import scala.reflect.ClassTag

import scala.runtime.Statics

abstract class VCBytePrototype(val underlying: Byte) extends VCPrototype {}

abstract class VCByteCasePrototype(underlying: Byte) extends VCBytePrototype(underlying) with Product1[Byte] {

  final def _1: Byte = underlying

  override final def hashCode(): Int = {
    underlying.hashCode()
  }

  override final def toString: String = {
    s"$productPrefix($underlying)"
  }

  // subclasses are expected to implement equals, productPrefix, and canEqual
}

abstract class VCByteCompanion[T <: VCBytePrototype] extends ClassTag[T] {
  def box(underlying: Byte): T
  final def unbox(boxed: T) = boxed.underlying
}

final class VCArrayByte[T <: VCBytePrototype](val ct: VCByteCompanion[T], sz: Int) extends VCArrayPrototype[T] {
  val arr = new Array[Byte](sz)
  def apply(idx: Int) =
    ct.box(arr(idx))
  def update(idx: Int, elem: T) =
    arr(idx) = ct.unbox(elem)
  def length: Int = arr.length

  override def toString: String = {
    "[" + ct.runtimeClass
  }

  // Todo: what was the reason for 255 classes in my original proposal? arr.toString!
  // todo: need to discuss do we want to be compatible with ugly format of jvm here?
}
