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
                .create(AccessToken::class.java)
                .getTestRequestResult(id, count)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    fun mediaNextRequest(url:String):Observable<InstagramModel>{
        return RemoteHelper.initRetrofit("https://instahot.herokuapp.com/")
                .create(AccessToken::class.java)
                .getNextRequestResult(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    fun salaryDataRequest():Observable<List<SalaryModel>>{
        return RemoteHelper.initRetrofit("https://thermal-works-186407.appspot.com/api/")
                .create(AccessToken::class.java)
                .getSalaryData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }
}

interface AccessToken{
    // https://instahot.herokuapp.com/toru_0239/media/?count=20
    @Headers("Referer: application/atom+xml")
    @GET("{id}/media/")
    fun getTestRequestResult(@Path("id") instgramId:String,
                             @Query("count") count:Int): Observable<InstagramModel>

    @Headers("Referer: application/atom+xml")
    @GET
    fun getNextRequestResult(@Url url:String):Observable<InstagramModel>

    @GET("salary")
    fun getSalaryData():Observable<List<SalaryModel>>
}