package com.doing.poet.java

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.doing.navigatorannotation.AddJavaCode
import com.doing.navigatorannotation.Destination
import com.doing.poet.PoetConstant
import com.doing.poet.R

@AddJavaCode
@Destination(pageUrl = PoetConstant.ACTIVITY_JAVA_POET)
class JavaPoetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_java_poet)

        // 由于APT生成类代码是在编译到 app 工程时才生成的代码， 所以这里直接引用会报错
        // HelloJavaPoet.main(arrayOf())
    }
}