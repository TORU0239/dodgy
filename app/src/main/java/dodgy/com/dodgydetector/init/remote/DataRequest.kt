package dodgy.com.dodgydetector.init.remote

import dodgy.com.dodgydetector.init.model.InstagramModel
import dodgy.com.dodgydetector.init.model.SalaryModel
import dodgy.com.dodgydetector.remote.RemoteHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.http.*

/**
 * Created by toruchoi on 18/11/2017.
 */
class DataRequest {
    fun mediaRequest(id:String, count:Int):Observable<InstagramModel>{
        return RemoteHelper.initRetrofit("https://instahot.herokuapp.com/")
                .create(DodgeService::class.java)
                .getTestRequestResult(id, count)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    fun mediaNextRequest(url:String):Observable<InstagramModel>{
        return RemoteHelper.initRetrofit("https://instahot.herokuapp.com/")
                .create(DodgeService::class.java)
                .getNextRequestResult(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    fun salaryDataRequest():Observable<List<SalaryModel>>{
        return RemoteHelper.initRetrofit("https://thermal-works-186407.appspot.com/api/")
                .create(DodgeService::class.java)
                .getSalaryData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    fun getDodgyUser(user:String):Observable<String>{
        return RemoteHelper.initRetrofit("https://thermal-works-186407.appspot.com/api/")
                .create(DodgeService::class.java)
                .getDodgyUserData(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    fun getDodgyVisionLabel(id:String):Observable<String>{
        return RemoteHelper.initRetrofit("https://thermal-works-186407.appspot.com/api/")
                .create(DodgeService::class.java)
                .getDodgyVisionLabel(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    fun getDodgeVisonLandmark():Observable<String>{
        return RemoteHelper.initRetrofit("https://thermal-works-186407.appspot.com/api/")
                .create(DodgeService::class.java)
                .getDodgeVisonLandmark()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    fun getDodgeVisonLogo():Observable<String>{
        return RemoteHelper.initRetrofit("https://thermal-works-186407.appspot.com/api/")
                .create(DodgeService::class.java)
                .getDodgeVisonLogo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }
}

interface DodgeService{
    @GET("salary")
    fun getSalaryData():Observable<List<SalaryModel>>

    // https://instahot.herokuapp.com/toru_0239/media/?count=20
    @Headers("Referer: application/atom+xml")
    @GET("{id}/media/")
    fun getTestRequestResult(@Path("id") instgramId:String,
                             @Query("count") count:Int): Observable<InstagramModel>

    @Headers("Referer: application/atom+xml")
    @GET
    fun getNextRequestResult(@Url url:String):Observable<InstagramModel>

    @GET("check-dodgy-user")
    fun getDodgyUserData(@Query("user") user:String):Observable<String>

    @GET("check-dodgy-cloud-vision-label")
    fun getDodgyVisionLabel(@Query("id")id:String):Observable<String>

    @GET("check-dodgy-cloud-vision-landmark")
    fun getDodgeVisonLandmark(@Query("id")id:String):Observable<String>

    @GET("check-dodgy-cloud-vision-logo")
    fun getDodgeVisonLogo(@Query("id")id:String):Observable<String>
}