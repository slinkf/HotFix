package com.omega.hotfix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
create by ldk in
15:22 20/02/20
 **/
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Util.todo()
    }
}
