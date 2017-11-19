package dodgy.com.dodgydetector.init.presenter

import android.util.Log
import dodgy.com.dodgydetector.init.remote.DataRequest
import dodgy.com.dodgydetector.init.view.InitView

/**
 * Created by toruchoi on 18/11/2017.
 */
interface InitPresenter {
    fun onRequestMedia(id:String, count:Int)
    fun onRequestSalaryModel()

    fun onRequestLabel(id:String)
    fun onRequestLandmark(id:String)
    fun onRequestLogo(id:String)
    fun onRequestText(id:String)

    fun onRequestDodgyUser(instagram:String)

//    https://thermal-works-186407.appspot.com/
}

class InitPresenterImp(private val view:InitView):InitPresenter{
    override fun onRequestDodgyUser(instagram: String) {
        DataRequest().getDodgyUser(instagram)
                .subscribe({
                    Log.w("TORU", "data: " + it)
                    view.onShowProgess("20")
                    onRequestLabel(it.id)
                }, {
                    t: Throwable? -> t?.printStackTrace()
                })
    }

    override fun onRequestLabel(id: String) {
        DataRequest().getDodgyVisionLabel(id)
                .subscribe({
                    Log.w("TORU", "data: " + it)
                    view.onShowProgess("40")
                    onRequestLandmark(id)
                }, {
                    t: Throwable? -> t?.printStackTrace()
                })
    }

    override fun onRequestLandmark(id: String) {
        DataRequest().getDodgeVisonLandmark(id)
                .subscribe({
                    Log.w("TORU", "data: " + it)
                    view.onShowProgess("60")
                    onRequestLogo(id)
                }, {
                    t: Throwable? -> t?.printStackTrace()
                })
    }

    override fun onRequestLogo(id: String) {
        DataRequest().getDodgeVisonLogo(id)
                .subscribe({
                    view.onShowProgess("80")
                    onRequestText(id)
                }, {
                    t: Throwable? -> t?.printStackTrace()
                })
    }

    override fun onRequestText(id: String) {
        DataRequest().getDodgeVisonText(id)
                .subscribe({
                    Log.w("TORU", "data: " + it)
                    view.onShowProgess("100")
                    view.onShowToast("SUCCESS!!! HE IS DODGY!")
                }, {
                    t: Throwable? -> t?.printStackTrace()
                })
    }

    override fun onRequestSalaryModel() {
        DataRequest().salaryDataRequest()
                .doOnSubscribe {
                    view.onShowToast("Loading Salary Data")
                }
                .doOnTerminate {
                    view.onShowToast("Salary Data Complete")
                }
                .subscribe({
                    data -> view.onUpdateSalaryInformation(data)
                }, {
                    t: Throwable? -> t?.printStackTrace()
                })
    }

    private var totalResult:Int = 0

    override fun onRequestMedia(id: String, count: Int) {
        totalResult = 0
        val request = DataRequest()
        request.mediaRequest(id, count)
                .doOnSubscribe {
                    view.onShowDialog()
                }
                .filter { it.posts.isNotEmpty() }
                .subscribe ({
                    totalResult += it.posts.size
                    view.onViewUpdate(it.posts)
                    Log.w("InitPresenter", "total result " + totalResult)
                    if(it.next != null && it.next.isNotEmpty()){
                        request.mediaNextRequest(it.next)
                                .subscribe {
                                    totalResult += it.posts.size
                                    Log.w("InitPresenter", "total result after recursion:: " + totalResult)
                                    view.onViewUpdate(it.posts)
                                    if(totalResult == count){
                                        view.onHideDialog()
                                    }
                                }
                    }
                    if(totalResult == count){
                        view.onHideDialog()
                    }
                }, {
                    t->t.printStackTrace()
                })
    }


}