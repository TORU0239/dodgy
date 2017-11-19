package dodgy.com.dodgydetector.init.view

import dodgy.com.dodgydetector.init.model.InstagramMediaModel
import dodgy.com.dodgydetector.init.model.SalaryModel

/**
 * Created by toruchoi on 18/11/2017.
 */
interface InitView {
    fun onViewUpdate(mediaList:List<InstagramMediaModel>)
    fun onShowToast(str:String)
    fun onShowDialog()
    fun onHideDialog()

    fun onUpdateSalaryInformation(salaryList:List<SalaryModel>)
}