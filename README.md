![RxVan Logo](https://github.com/yundom/RxVan/blob/master/images/logo.png)

# RxVan
[![CircleCI branch](https://circleci.com/gh/yundom/RxVan.svg?style=shield&circle-token=a3cf3f1009b4fb1b1d4aace674682a9d010e18c8)](https://circleci.com/gh/yundom/RxVan/tree/master)
[![GitHub license](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A lightweight event bus written in Kotlin.

> Usually, a van is big enough to carry all.

## Installation
Gradle:
```
implementation 'com.yundom:RxVan:1.0.1'
```

Maven:
```
<dependency>
  <groupId>com.yundom</groupId>
  <artifactId>rxvan</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```

## Usage
### Step 1: Define event type

``` kotlin
data class Pizza(val pizzaSlice: String)
```

### Step 2: Start observing the event

``` kotlin
RxVan.get().observe<Pizza>().subscribe({
  println("Eat ${it.pizzaSlice}")
})
```

### Step 3: Post events
``` kotlin
RxVan().get().post(Pizza("Margherita"))
```

## How to register and unregister your observer
``` kotlin
class PizzaOrderActivity : Activity {

  private val subscription: Disposable? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    // You can keep the subscription in a disposable.
    subscription = RxVan.get().observe<Pizza>().subscribe({
      println("Eat ${it.pizzaSlice}")
    })
  }

  override fun onDestroy() {
    // Then dispose it when no longer needed.
    subscription?.dispose()
  }
}
```

## License
```text
The MIT License (MIT)

Copyright (c) 2018 Dennis Hsieh

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

```
