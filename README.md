# PowerView
A pretty charging view.


### [中文](https://github.com/samlss/PowerView/blob/master/README-ZH.md)

<br/>

[![Api reqeust](https://img.shields.io/badge/api-1+-green.svg)](https://github.com/samlss/PowerView)  [![Apache License 2.0](https://img.shields.io/hexpm/l/plug.svg)](https://github.com/samlss/PowerView/blob/master/LICENSE) [![Blog](https://img.shields.io/badge/samlss-blog-orange.svg)](https://blog.csdn.net/Samlss)

### Here's the HORIZONTAL effect:
![gif1](https://github.com/samlss/PowerView/blob/master/screenshots/screenshot2.gif)

### Here's the VERTICAL effect:
![gif2](https://github.com/samlss/PowerView/blob/master/screenshots/screenshot1.gif)


## Use<br>
Add it in your root build.gradle at the end of repositories：
```
allprojects {
    repositories {
        //...
        maven { url 'https://jitpack.io' }
    }
}
```

Add it in your app build.gradle at the end of repositories:
```
dependencies {
    implementation 'com.github.samlss:PowerView:1.0'
}
```

### Attributes description：

| attr        | description           |
| ------------- |:-------------:|
| powerColor      | the color of charging power  |
| bgColor | the color of battery background |
| powerProgress | the progress |
| orientation | the orientation(horizontal/vertical)  |

<br/>


### in layout.xml
```
    <com.iigo.library.PowerView
              android:layout_width="150dp"
              android:layout_height="65dp"
              app:bgColor="@color/colorPrimary"
              app:powerColor="@color/colorAccent"
              app:orientation="horizontal"
              app:powerProgress="5" />
```

### Used in the code:
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
