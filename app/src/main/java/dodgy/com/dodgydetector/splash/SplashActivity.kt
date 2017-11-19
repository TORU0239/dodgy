package dodgy.com.dodgydetector.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import dodgy.com.dodgydetector.R
import dodgy.com.dodgydetector.init.view.InitActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        goOnNext()
    }

    private fun goOnNext(){
        Handler().postDelayed({
            val intent = Intent(SplashActivity@this, InitActivity::class.java)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(SplashActivity@this,
                    splash_image, ViewCompat.getTransitionName(splash_image))
            // noinspection RestrictedApi
            startActivityForResult(intent, 1002, options.toBundle())
        }, 1500)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == 1002){
            finish()
        }
    }

    override fun onDestroy() {
        if(!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
        super.onDestroy()
    }
}