# SwipeDrawer  &middot; [![Star](https://img.shields.io/github/stars/Leaqi/SwipeDrawer?color=fb6698&label=Star "Star")](https://github.com/Leaqi/SwipeDrawer/stargazers "Star") [![Fork](https://img.shields.io/github/forks/Leaqi/SwipeDrawer?color=2196f3&label=Fork "Fork")](https://github.com/Leaqi/SwipeDrawer/network "Fork") [![API](https://img.shields.io/badge/API-14%2B-04d8bb "API")](https://github.com/Leaqi/SwipeDrawer/ "API") [![JitPack](https://jitpack.io/v/Leaqi/SwipeDrawer.svg "JitPack")](https://jitpack.io/#cn.Leaqi/SwipeDrawer "JitPack") [![Release](https://img.shields.io/github/v/release/Leaqi/SwipeDrawer?label=Release&color=06da47 "Release")](https://github.com/Leaqi/SwipeDrawer/releases "Release") [![License](https://img.shields.io/badge/License-Apache--2.0-e0b003 "License")](https://github.com/Leaqi/SwipeDrawer/blob/main/LICENSE "License") [![Download Demo Apk](https://img.shields.io/badge/Download-Demo%20Apk-45c703 "Download Demo Apk")](https://github.com/Leaqi/SwipeDrawer/releases/download/1.0/demo.apk "Download Demo Apk")
#### English README | [中文 README](https://github.com/Leaqi/SwipeDrawer/ "中文 README")
Android SwipeDrawer sliding drawer library can add up, down, left and right drawer layouts at the same time. There are three drawer opening modes: drawer mode, overlay mode and fixed mode. It supports unlimited nesting and edge sliding opening. Swipedrawer can also be used as a drop-down refresh layout, and supports listview, recyclerview, GridView, Scrollview, etc.

#### English：
[![Click to view Use Docs](https://img.shields.io/badge/SwipeDrawer-Use%20Docs-blue "Click to view Use Docs")](https://Leaqi.github.io/SwipeDrawer_en/ "Click to view Use Docs") [![Click to view Detailed Docs](https://img.shields.io/badge/SwipeDrawer-Detailed%20Docs-orange "Click to view Detailed Docs")](https://Leaqi.github.io/SwipeDrawer_en/code.html "Click to view Detailed Docs") [![Click to view Picture Preview](https://img.shields.io/badge/SwipeDrawer-Picture%20Preview-green "Click to view Picture Preview")](https://Leaqi.github.io/SwipeDrawer_en/pics.html "Click to view Picture Preview")

#### 中文：
[![点击查看使用文档](https://img.shields.io/badge/SwipeDrawer-%E4%BD%BF%E7%94%A8%E6%96%87%E6%A1%A3-blue "点击查看使用文档")](https://Leaqi.github.io/SwipeDrawer/ "点击查看使用文档") [![点击查看功能详解](https://img.shields.io/badge/SwipeDrawer-%E5%8A%9F%E8%83%BD%E8%AF%A6%E8%A7%A3-orange "点击查看功能详解")](https://Leaqi.github.io/SwipeDrawer/code.html "点击查看功能详解") [![点击查看图片演示](https://img.shields.io/badge/SwipeDrawer-%E5%9B%BE%E7%89%87%E6%BC%94%E7%A4%BA-green "点击查看图片演示")](https://Leaqi.github.io/SwipeDrawer/pics.html "点击查看图片演示")

## Setup
Add `jitpack` repositories：

    allprojects {
        repositories {
            ...
            maven { url "https://jitpack.io" }
        }
    }
	
Add `SwipeDrawer` dependencies：

    dependencies {
        ...
        implementation 'cn.Leaqi:SwipeDrawer:1.1'
    }

Add `SwipeDrawer` to the layout file ：

    <cn.leaqi.drawer.SwipeDrawer
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:leftLayout="@+id/leftDrawer">
    
        <!-- The leftLayout property specifies the left layout -->
        <LinearLayout
            android:id="@+id/leftDrawer"
            android:layout_width="200dp"
            android:layout_height="match_parent"
            android:background="#FF5722">
            <TextView
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:gravity="center"
                android:text="Left" />
        </LinearLayout>
    
        <!-- By default, the first layout without ID is the main layout -->
        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">
            <TextView
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:gravity="center"
                android:text="Main" />
        </RelativeLayout>
    </cn.leaqi.drawer.SwipeDrawer>

## Picture preview &middot; [![See More](https://img.shields.io/badge/See-More-blue "See More")](https://Leaqi.github.io/SwipeDrawer/pics.html "See More")
[![Demo](https://p.ssl.qhimg.com/t0127d8ad395f6aff6f.jpg "Demo")](https://Leaqi.github.io/SwipeDrawer/pics.html "Demo") [![Demo](https://p.ssl.qhimg.com/t017c6a31eaa1242d0a.jpg "Demo")](https://Leaqi.github.io/SwipeDrawer/pics.html "Demo") [![Demo](https://p.ssl.qhimg.com/t0131cdd3e8c2ca7d41.jpg "Demo")](https://Leaqi.github.io/SwipeDrawer/pics.html "Demo") [![Demo](https://p.ssl.qhimg.com/t019aa5b68b427443c9.jpg "Demo")](https://Leaqi.github.io/SwipeDrawer/pics.html "Demo")

## License
[Apache-2.0 License](https://github.com/Leaqi/SwipeDrawer/blob/main/LICENSE "Apache-2.0 License")

Copyright (c) 2022 Leaqi