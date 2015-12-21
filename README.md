Simple MVVP DataBinding with RxJava Weather App [level: Beginner]
==========================

This is a simple one screen weather app to save you a little time if you plan to work with data-bindings.

<img src="https://raw.githubusercontent.com/AndreiD/MVVP-WeatherApp/master/screenshot.png" alt="Drawing" style="width: 200px;"/>
![drawing](drawing.jpg) ("How the app looks 1")


#### How to use it:

* Clone it from github & play with it
* Star this repository :)


#### What it contains:

~~~~

dependencies {

    compile fileTree(include: ['*.jar'], dir: 'libs')

    compile 'com.android.support:appcompat-v7:23.1.1'
    compile "com.android.support:design:23.1.1"
    compile "com.android.support:design:23.1.1"
    compile "com.android.support:recyclerview-v7:23.1.1"

    //----- Rx
    compile "io.reactivex:rxandroid:1.1.0"
    compile "io.reactivex:rxjava:1.1.0"
    compile 'com.jakewharton.rxbinding:rxbinding:0.3.0'

    //----- Retrofit
    compile "com.squareup.retrofit:retrofit:2.0.0-beta2"
    compile "com.squareup.retrofit:converter-gson:2.0.0-beta2"
    compile "com.squareup.retrofit:adapter-rxjava:2.0.0-beta2"
    compile 'com.squareup.okhttp:logging-interceptor:2.6.0'

    //----- Timber
    compile 'com.jakewharton.timber:timber:4.1.0'

    //----- Testing
    androidTestCompile 'com.android.support:support-annotations:23.0.1'
    androidTestCompile 'com.android.support.test:runner:0.4.1'
    androidTestCompile 'com.android.support.test.uiautomator:uiautomator-v18:2.1.1'
    androidTestCompile 'org.hamcrest:hamcrest-integration:1.3'
}

~~~~

#### Need more nice stuff ?

- Google, Facebok, Twitter logins -> https://github.com/AndreiD/FacebookTwitterGoogleLogins
- A survey lib for your app -> https://github.com/AndreiD/surveylib

#### Updates, Questions, and Requests

- ActivityMainBinding is automaticly generated when you run the app.


#### You like this library ? Check
- https://github.com/AndreiD/surveylib - A very good looking survey library
- https://github.com/AndreiD/TSnackBar Snackbar from the top


#### Thank you:

- Icons by http://vclouds.deviantart.com/


#### License

~~~~
Copyright 2015 AndroidAdvance.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
~~~~