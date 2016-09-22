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
package tests

class BuiltinAtomSpec extends BaseSpec {

  "Primitives" in {
    val atoms = Vector(
      // primitives:
      Atom[Int],
      Atom[Long],
      Atom[Float],
      // JDK types:
      Atom[String]
    )
    atoms.map(_.uuid).toSet should have size atoms.size.toLong
    atoms.foreach { a =>
      AtomRegistry.builtinAtomRegistry.getAtom(a.uuid).fold(
        err => fail(err),
        b => b shouldBe theSameInstanceAs (a)
      )
    }
  }
}