package com.omega.hotfix

import android.app.Application
import android.content.Context

class MApp:Application(){

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        HotFix.fixBug(this,"")
    }

}