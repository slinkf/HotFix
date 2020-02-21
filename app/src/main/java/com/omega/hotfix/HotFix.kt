package com.omega.hotfix

import android.app.Application
import android.graphics.NinePatch
import android.util.Log
import java.io.File
import java.io.IOException
import java.lang.reflect.Field
import java.lang.reflect.Method

object HotFix {

    fun fixBug(application:Application,patch:File){
        val classLoader = application.classLoader
        //反射拿到DexPathList对象
        val dexPathList = findFiled(classLoader, "pathList").get(classLoader)

        val dexElements = findFiled(dexPathList!!, "dexElements")
        val findMethod = findMethod(
            dexPathList,
            "makeDexElements",
            ArrayList::class.java,
            File::class.java,
            ArrayList::class.java
        )
        //造出来一个Element
        val files = ArrayList<File>()
        files.add(patch)
        val ioException = ArrayList<IOException>()
        //自己得到的element
        val element:Array<*> = findMethod.invoke(findMethod,files,application.filesDir,ioException) as Array<*>
        val elementArray:Array<*> = dexElements.get(dexPathList) as Array<*>
        val newElementArray =java.lang.reflect.Array.newInstance(
            element.javaClass.componentType!!,
            element.size+elementArray.size)
        Log.i("elementLength","${element.size}----${application.filesDir.path}")
        System.arraycopy(element,0,newElementArray,0,element.size)
        System.arraycopy(elementArray,0,newElementArray,element.size,elementArray.size)

        dexElements.set(dexPathList,newElementArray)
    }


    fun findFiled(classLoader:Any,name:String):Field{
        var javaClass:Class<*>? = classLoader.javaClass

        while(javaClass!=null){
               try {
                   val declaredField = javaClass.getDeclaredField(name)
                   if (declaredField!=null){
                       declaredField.isAccessible=true
                       return declaredField
                   }
               }catch (e:NoSuchFieldException){

               }
            javaClass = javaClass.superclass
        }
        throw NoSuchMethodException("没有找到相应的属性$name")
    }

    fun findMethod(classLoader:Any,name:String,vararg paramsObj:Class<*>):Method{
        var javaClass:Class<*>? = classLoader.javaClass
        while (javaClass!=null){
            try {
                val declaredMethod = javaClass.getDeclaredMethod(name,*paramsObj)
                if (declaredMethod!=null){
                    declaredMethod.isAccessible=true
                    return declaredMethod
                }
            }catch(e:NoSuchMethodException){

            }
            javaClass=javaClass.superclass
        }
        throw  NoSuchMethodException("没有找到相对的方法 $name")
    }



}