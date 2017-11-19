package dodgy.com.dodgydetector.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dodgy.com.dodgydetector.R
import dodgy.com.dodgydetector.init.view.InitActivity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
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
        compositeDisposable.add(Observable.just(this)
                .delay(1500, TimeUnit.MILLISECONDS)
                .map {
                    startActivity(Intent(SplashActivity@this, InitActivity::class.java))
                    finish()
                }
                .subscribe())
    }

    override fun onDestroy() {
        if(!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
        super.onDestroy()
    }
}