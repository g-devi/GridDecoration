# GridDecoration
AndroidのRecyclerViewでGridLayoutを用いた際に、要素間の余白を調整するItemDecoration。


画面左右端の余白と要素間の余白を合わせるのに役立ちます。

全幅のコンテンツも配置できるようになります。  


Usage
-----

#### 通常
```java
GridDecoration.set(recyclerView, visibleMargin);
```
* visibleMarginで要素間に設定したい余白(px)を渡す

#### 要素のViewGroupに余白(layout_marginなど)が存在する場合
```java
GridDecoration.set(recyclerView, visibleMargin, contentMargin);
```
* contentMarginで要素に設定した余白(px)を渡す
* 要素に設定された余白も含めてvisibleMargin分の余白を空けるように調整される

#### 要素上下と左右の余白を変えたい場合
```java
GridDecoration.setVH(RecyclerView recycler,
            verticalVisibleMargin, horizontalVisibleMargin,
            verticalContentMargin, horizontalContentMargin);
```
* 上下(vertical)と左右(horizontal)に設定したい余白と、コンテンツの余白を設定する



Download
--------

coming soon

```groovy
// compile 'com.github.g_devi:griddecoration:1.0.0'
```


License
-------

    Copyright 2017 g-devi

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
