package com.github.imliar.slick.repository.generic

import com.github.imliar.slick.repository.Entity
import shapeless.ops.record.Selector
import shapeless.{Witness, HList, LabelledGeneric, DepFn0}

trait EntityGen[E] extends DepFn0 {

  type Id
  type Out = Entity[E, Id]

}

object EntityGen {

  type Aux[E, I] = EntityGen[E] {
    type Id = I
  }

  implicit def mkEntity[E, I, H <: HList](implicit
                                           gen: LabelledGeneric.Aux[E, H],
                                           selector: Selector.Aux[H, Witness.`'id`.T, Option[I]]): EntityGen.Aux[E, I] = new EntityGen[E] {
    type Id = I

    def apply(): Out = {
      new Entity[E, Id] {
        override def id(e: E): Option[Id] = {
          val repr = gen.to(e)
          selector(repr)
        }
      }
    }

  }
}