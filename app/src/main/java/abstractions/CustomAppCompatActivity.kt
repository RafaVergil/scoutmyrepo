package abstractions

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
    One class to rule them all.
    It is actually the Activity super class that is used when we need all the other Activities
    to do something similar, like displaying a "Network unavailable" dialog, a "Loading" dialog
    or naming a TAG.
*/
open class CustomAppCompatActivity : AppCompatActivity() {
    val TAG: String = CustomAppCompatActivity::class.java.simpleName
//    lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        loadingDialog = LoadingDialog(this@CustomAppCompatActivity)
    }
}