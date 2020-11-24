package com.fengyongge.gank

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fengyongge.gank.activity.GirlActivity
import kotlinx.android.synthetic.main.gank_activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gank_activity_main)

        tvTest.setOnClickListener {
            startActivity(Intent(this,GirlActivity::class.java))
        }
    }
}