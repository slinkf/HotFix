package com.omega.hotfix

import android.app.Application
import android.content.Context
import java.io.File

class MApp:Application(){

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        HotFix.fixBug(this,File("patch.jar"))
    }

}