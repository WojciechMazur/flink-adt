package io.findify.flinkadt.instances

import io.findify.flinkadt.instances.SetSerializer.SetSerializerSnapshot
import org.apache.flink.api.common.typeutils.{ SimpleTypeSerializerSnapshot, TypeSerializer, TypeSerializerSnapshot }
import org.apache.flink.core.memory.{ DataInputView, DataOutputView }

class SetSerializer[T](child: TypeSerializer[T]) extends SimpleSerializer[Set[T]] {
  override def createInstance(): Set[T] = Set.empty[T]
  override def getLength: Int = -1
  override def deserialize(source: DataInputView): Set[T] = {
    val count = source.readInt()
    val result = for {
      _ <- 0 until count
    } yield {
      child.deserialize(source)
    }
    result.toSet
  }
  override def serialize(record: Set[T], target: DataOutputView): Unit = {
    target.writeInt(record.size)
    record.foreach(element => child.serialize(element, target))
  }
  override def snapshotConfiguration(): TypeSerializerSnapshot[Set[T]] = new SetSerializerSnapshot(this)

}

object SetSerializer {
  case class SetSerializerSnapshot[T](self: TypeSerializer[Set[T]])
      extends SimpleTypeSerializerSnapshot[Set[T]](() => self)
}
