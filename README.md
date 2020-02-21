# HotFix
利用反射 类加载进行热修复的代码

实现思路如下
1:通过得到PathClassLoader的私有静态属性 DexPathList
2:拿到DexPathList的Element数组
3:获取补丁路径，使用DexPathList的makeDexElement方法将我们的补丁转化为Element
4:新建一个Element数组 将补丁的Element插入到数组的最前面 把EexPathList中的Element放到后面
5:把新建的Element赋值到DexPathList的Element数组中 就完成了

打补丁命令： dx --dex --output=patch.jar Util.kt

这只是一个特别简单的基于类加载 和反射实现的热修复  没有做任何的android版本适配  在android不同的版本有不同的资源加载特性，这些适配的繁重的代码都没做！

这个demo仅供参考学习 不能用作商用

android 源码请参考 http://androidxref.com