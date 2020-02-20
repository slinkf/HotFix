package com.omega.hotfix

import android.app.Application
import java.io.File
import java.lang.reflect.Field
import java.lang.reflect.Method

object HotFix {

    fun fixBug(application:Application,path:String){
        val classLoader = application.classLoader
        //反射拿到DexPathList对象
        val dexPathList = findFiled(classLoader, "pathList").get(classLoader)
        val dexElements = findFiled(dexPathList.javaClass.classLoader!!, "dexElements")
        val findMethod = findMethod(
            dexPathList,
            "makeDexElements",
            ArrayList::class.java,
            File::class.java,
            ArrayList::class.java
        )
    }


    fun findFiled(classLoader:Any,name:String):Field{
        var javaClass:Class<Any>? = classLoader.javaClass

        while(javaClass!=null){
                val declaredField = javaClass.getDeclaredField(name)
            if (declaredField!=null){
                declaredField.isAccessible=true
                return declaredField
            }
            javaClass = javaClass.superclass
        }
        throw NoSuchMethodException("没有找到相应的属性$name")
    }

    fun findMethod(classLoader:Any,name:String,vararg paramsObj:Class<*>):Method{
        var javaClass:Class<Any>? = classLoader.javaClass
        while (javaClass!=null){
            val declaredMethod = javaClass.getDeclaredMethod(name,*paramsObj)
            if (declaredMethod!=null){
                declaredMethod.isAccessible=true
                return declaredMethod
            }
            javaClass=javaClass.superclass
        }
        throw  NoSuchMethodException("没有找到相对的方法 $name")
    }



}