/*
 * Copyright 2017 Daniel Urban and contributors listed in AUTHORS
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

package io.sigs.seals.tests;

enum MyTestEnumWithOverloads {

	FOO(0.05f),
	BAR(4.56f);
	
	final float delta;
	
	private MyTestEnumWithOverloads(float delta) {
		this.delta = delta;
	}
	
	/** Overloads the generated valueOf(String) method */
	static MyTestEnumWithOverloads valueOf(float delta) {
		return null;
	}
	
	/** Overloads the generated valueOf(String) method */
	static MyTestEnumWithOverloads valueOf(int idx) {
		return null;
	}
}
