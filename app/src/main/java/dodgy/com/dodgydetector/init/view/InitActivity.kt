package dodgy.com.dodgydetector.init.view

import android.animation.Animator
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
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.util.Log


class InitActivity : AppCompatActivity(), InitView {

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

    lateinit var outAnimator: Animator
    lateinit var inAnimator: Animator
    var backisvisible = false

    private fun loadingAnimation(){
        outAnimator = AnimatorInflater.loadAnimator(this, R.animator.out_animation)
        inAnimator = AnimatorInflater.loadAnimator(this, R.animator.in_animation)
    }

    private fun changeCameraDistance(){
        val distance = 8000
        val scale = resources.displayMetrics.density * distance
        layout_report.cameraDistance = scale
//        loading_layout.cameraDistance = scale
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init)
        loadingAnimation()
        changeCameraDistance()

        reportingBtn.setOnClickListener {
            if(instagramET.editableText.toString().isEmpty()){
                Toast.makeText(InitActivity@this, "No Instagram ID.", Toast.LENGTH_SHORT).show()
            }

            else if(jobPositionAutoText.editableText.toString().isEmpty()){
                Toast.makeText(InitActivity@this, "No Job Position.", Toast.LENGTH_SHORT).show()
            }

            else {
                length = 0
                presenter.onRequestMedia(instagramET.editableText.toString(), 50)
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromInputMethod(instagramET.windowToken, 0)
                imm.hideSoftInputFromInputMethod(jobPositionAutoText.windowToken, 0)

                Log.w(TAG, "testz")

                loading_layout.visibility = View.VISIBLE
            }
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