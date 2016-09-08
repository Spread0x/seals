/*
 * Copyright 2016 Daniel Urban
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.sigs.seals
package laws

import java.util.UUID

import cats.Eq

object TestTypes {

  final case object Whatever

  object adts {

    object iso {

      sealed trait Adt1
      object Adt1 {
        final case class Foo(s: String, i: Int) extends Adt1
        object Foo {
          val expModel =
            's -> Atom[String] :: 'i -> Atom[Int] :: Model.HNil
        }
        final case object Boo extends Adt1
        val expModel =
          'Boo -> Model.HNil :+: 'Foo -> ('s -> Atom[String] :: 'i -> Atom[Int] :: Model.HNil) :+: Model.CNil
      }

      sealed trait Adt2
      object Adt2 {
        final case class Foo(s: String, i: Int) extends Adt2
        final case object Boo extends Adt2
      }
    }

    object defs {

      sealed trait Adt1
      object Adt1 {
        final case object Dummy extends Adt1
        final case class C(a: Int, b: String = "boo") extends Adt1

        implicit val adt1Eq: Eq[Adt1] =
          Eq.fromUniversalEquals
      }

      sealed trait Adt2
      object Adt2 {
        final case object Dummy extends Adt2
        final case class C(a: Int, b: String = "boo", c: Float = 0.5f) extends Adt2
        object C {
          val expModel = {
            'a -> Atom[Int] :: Model.HCons(
              'b,
              true,
              Atom[String],
              Model.HCons('c, true, Atom[Float], Model.HNil)
            )
          }
        }

        val expModel = {
          'C -> C.expModel :+: 'Dummy -> Model.HNil :+: Model.CNil
        }
      }

      sealed trait Adt3
      object Adt3 {
        final case object Dummy extends Adt3
        final case class C(a: Int) extends Adt3
      }
    }

    object defsComp {
      final case class Person1(name: String, age: Int)
      final case class Person2(name: String, age: Int, address: Option[String] = None)
      final case class C1(i: Int, p: Person1)
      final case class C2(i: Int, p: Person2, x: Float = 0.5f)
    }

    object defsCompDefs {
      final case class Person1(name: String, age: Int)
      val empty1 = Person1("", 0)
      final case class Person2(name: String, age: Int, address: Option[String] = None)
      val empty2 = Person2("", 0)
      final case class C1(i: Int, p: Person1 = empty1)
      final case class C2(i: Int, p: Person2 = empty2, x: Float = 0.5f)
    }

    object nodefs {

      sealed trait Adt1
      object Adt1 {
        final case object Dummy extends Adt1
        final case class C(a: Int, b: String) extends Adt1
      }

      sealed trait Adt2
      object Adt2 {
        final case object Dummy extends Adt2
        final case class C(a: Int, b: String, c: Float) extends Adt2
      }

      sealed trait Adt3
      object Adt3 {
        final case object Dummy extends Adt3
        final case class C(a: Int) extends Adt3
      }
    }

    object recursive {

      sealed trait IntList
      object IntList {
        implicit val intListEq: Eq[IntList] =
          Eq.fromUniversalEquals
        lazy val expModel: Model.Coproduct = {
          'IntCons -> Model.HCons('head, Atom[Int], Model.HCons('tail, expModel, Model.HNil)) :+:
          'IntNil -> (Model.HNil) :+:
          Model.CNil
        }
      }
      final case object IntNil extends IntList
      final case class IntCons(head: Int, tail: IntList) extends IntList

      object v2 {
        sealed trait IntList
        final case object IntNil extends IntList
        final case class IntCons(head: Int, tail: IntList, dummy: Int = 42)
          extends IntList
      }

      final case class MutRec1(x: Option[MutRec2])
      final case class MutRec2(x: Option[MutRec1])

      final case class Deep1(i: Int, d: Deep2)
      final case class Deep2(s: String, d: Deep3)
      final case class Deep3(d: Deep4)
      final case class Deep4(dd: Deep1)
    }

    object rename {

      final case class C1(a: Int, b: String)
      final case class C2(a: Int, c: String)

      object v1 {
        sealed trait Adt
        final case class C(i: Int) extends Adt
        final case object D extends Adt
      }

      object v2 {
        sealed trait Adt
        final case class X(i: Int) extends Adt
        final case object D extends Adt
      }
    }
  }

  object custom {

    final case class WithUuid(i: Int, u: UUID)
    object WithUuid {
      val expModel = {
        import TestInstances.atomic.atomicUUID
        'i -> Atom[Int] :: 'u -> Atom[UUID] :: Model.HNil
      }
    }

    final case class NonSerializableDefault(i: Int, ns: NonSer = NonSer.empty)
    final class NonSer(val s: String)
    object NonSer {
      val empty: NonSer = new NonSer("")
    }
  }
}