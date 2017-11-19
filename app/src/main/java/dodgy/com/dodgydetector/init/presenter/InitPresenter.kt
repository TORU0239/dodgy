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

    fun onRequestLabel()

    fun onRequestLandmark()
    fun onRequestLogo()
    fun onRequestText()

//    https://thermal-works-186407.appspot.com/
}

class InitPresenterImp(private val view:InitView):InitPresenter{
    override fun onRequestLabel() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRequestLandmark() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRequestLogo() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRequestText() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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