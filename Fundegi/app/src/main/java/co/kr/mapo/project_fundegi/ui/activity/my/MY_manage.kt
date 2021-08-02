package co.kr.mapo.project_fundegi.ui.activity.my

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import co.kr.mapo.project_fundegi.R
import co.kr.mapo.project_fundegi.data.AppDatabase
import co.kr.mapo.project_fundegi.databinding.ActivityMyManageBinding
import co.kr.mapo.project_fundegi.ui.adapter.SavingManageAdapter
import co.kr.mapo.project_fundegi.ui.viewmodel.SavingViewModel

class MY_manage : AppCompatActivity() {

    private val savingViewModel : SavingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMyManageBinding>(this, R.layout.activity_my_manage)
        with(binding) {
            viewModel = savingViewModel
            lifecycleOwner = this@MY_manage
        }

    }

    fun goBack(v: View) {
        finish()
    }

}