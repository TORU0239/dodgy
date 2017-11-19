package dodgy.com.dodgydetector.init.view

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import dodgy.com.dodgydetector.R
import dodgy.com.dodgydetector.init.model.InstagramMediaModel
import dodgy.com.dodgydetector.init.model.SalaryModel
import dodgy.com.dodgydetector.init.presenter.InitPresenterImp
import kotlinx.android.synthetic.main.activity_init.*
import kotlinx.android.synthetic.main.finish_dialog.*
import kotlinx.android.synthetic.main.modal_progress.*


class InitActivity : AppCompatActivity(), InitView {
    override fun onShowProgess(percent: String) {
        if(percent == "100"){
            report_btn.visibility = View.VISIBLE
        }
        percentage.text = percent
    }

    private val presenter:InitPresenterImp by lazy{
        InitPresenterImp(this)
    }

    private fun showAndHide(show:Boolean){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if(!show){
            imm.hideSoftInputFromInputMethod(currentFocus.windowToken, 0)
        }
        else{
            imm.showSoftInputFromInputMethod(currentFocus.windowToken, 0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init)

        reportingBtn.setOnClickListener {
            if(instagramET.editableText.toString().isEmpty()){
                Toast.makeText(InitActivity@this, "No Instagram ID.", Toast.LENGTH_SHORT).show()
            }
            else if(jobPositionAutoText.editableText.toString().isEmpty()){
                Toast.makeText(InitActivity@this, "No Job Position.", Toast.LENGTH_SHORT).show()
            }
            else {
                length = 0
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromInputMethod(instagramET.windowToken, 0)
                imm.hideSoftInputFromInputMethod(jobPositionAutoText.windowToken, 0)

                presenter.onRequestDodgyUser(instagramET.editableText.toString())
                loading_layout.visibility = View.VISIBLE
            }
        }

        report_btn.setOnClickListener {
            layout_report.visibility = View.GONE
            loading_layout.visibility = View.GONE
            finish_layout.visibility = View.VISIBLE
        }

        finish_btn.setOnClickListener{
            layout_report.visibility = View.VISIBLE
            loading_layout.visibility = View.GONE
            finish_layout.visibility = View.GONE
        }

        presenter.onRequestSalaryModel()
    }

    private var length = 0;
    override fun onViewUpdate(mediaList: List<InstagramMediaModel>) {
        val size = mediaList.size
        when(size){
            0 -> onShowToast("No Photo!")
            1 -> onShowToast("Sent 1 photo")
            else -> {
                length += size
                onShowToast("Sent $length photos")
            }
        }
    }

    override fun onShowToast(str: String) {
        Toast.makeText(InitActivity@this, str, Toast.LENGTH_SHORT).show()
    }

    override fun onShowDialog() {
        Toast.makeText(InitActivity@this, "Loading Start!!", Toast.LENGTH_SHORT).show()
    }

    override fun onHideDialog() {
        Toast.makeText(InitActivity@this, "Loading End!!!", Toast.LENGTH_SHORT).show()
    }

    override fun onUpdateSalaryInformation(salaryList: List<SalaryModel>) {
        val salary = salaryList.map {
            salaryModel: SalaryModel -> salaryModel.role
        }.toList()

        val adapter = ArrayAdapter<String>(InitActivity@this, android.R.layout.select_dialog_item, salary)
        jobPositionAutoText.run{
            setAdapter(adapter)
            threshold = 1
        }
    }

    companion object {
        private val TAG = InitActivity::class.java.getSimpleName()
    }
}