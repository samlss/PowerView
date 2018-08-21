
# PowerView
一个简约的充电view

<br/>

[![Api reqeust](https://img.shields.io/badge/api-1+-green.svg)](https://github.com/samlss/PowerView)  [![Apache License 2.0](https://img.shields.io/hexpm/l/plug.svg)](https://github.com/samlss/PowerView/blob/master/LICENSE) [![Blog](https://img.shields.io/badge/samlss-blog-orange.svg)](https://blog.csdn.net/Samlss)

### 水平效果:
![gif1](https://github.com/samlss/PowerView/blob/master/screenshots/screenshot2.gif)

### 垂直效果:
![gif2](https://github.com/samlss/PowerView/blob/master/screenshots/screenshot1.gif)


## 使用<br>
在根目录的build.gradle添加这一句代码：
```
allprojects {
    repositories {
        //...
        maven { url 'https://jitpack.io' }
    }
}
```

在app目录下的build.gradle添加依赖使用：
```
dependencies {
    implementation 'com.github.samlss:PowerView:1.0'
}
```

### 属性说明：

| 属性        | 说明           |
| ------------- |:-------------:|
| powerColor      | 电量颜色  |
| bgColor | 背景颜色 |
| powerProgress | 当前电量(0-100) |
| orientation | 方向设置(水平/垂直) |

<br/>


### 布局中使用
```
    <com.iigo.library.PowerView
              android:layout_width="150dp"
              android:layout_height="65dp"
              app:bgColor="@color/colorPrimary"
              app:powerColor="@color/colorAccent"
              app:orientation="horizontal"
              app:powerProgress="5" />
```

### 代码中使用：
```
   powerView.setProgress(progress); //set current progress
   powerView.setBgColor(Color.BLACK); //set the color of battery background
   powerView.setPowerColor(Color.RED); //set the color of charging power.
```


### License

```
Copyright 2018 samlss

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
