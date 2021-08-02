package co.kr.mapo.project_fundegi.system

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.Spannable
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import co.kr.mapo.project_fundegi.R
import co.kr.mapo.project_fundegi.data.InformSavingEntity
import co.kr.mapo.project_fundegi.ui.adapter.SavingManageAdapter
import co.kr.mapo.project_fundegi.ui.adapter.TimelineAdapter
import co.kr.mapo.project_fundegi.ui.viewmodel.SavingViewModel
import com.bumptech.glide.Glide
import java.text.DecimalFormat

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-07-12
 * @desc
 */
object BindingAdapter {

    @JvmStatic
    @BindingAdapter("gif")
    fun setGif(view: ImageView, resource:Int) {
        Glide.with(view.context).load(resource).into(view)
    }

    @JvmStatic
    @BindingAdapter("cardBackgroundColor")
    fun setCardBackground(cardView: CardView, saving : InformSavingEntity) {
        cardView.setCardBackgroundColor(
            Color.parseColor(
                if(saving.endDate.isBlank()) when (saving.id) {
                    1 -> "#4db6ac"
                    2 -> "#ffeb3b"
                    3 -> "#cddc39"
                    else -> "#eb6940"
                } else "#e5e5e5"
            )
        )
    }

    @JvmStatic
    @BindingAdapter("cardTextColor")
    fun setCardTextColor(textView: TextView, saving : InformSavingEntity) {
        textView.setTextColor(
            Color.parseColor(
                if(saving.endDate.isBlank()) when (saving.id) {
                    1, 4 -> "#FFFFFF"
                    else -> "#606060"
                } else "#606060"
            )
        )
    }

    @JvmStatic
    @BindingAdapter("closeButtonColor")
    fun setCloseButtonClore(imageView: ImageView, saving : InformSavingEntity) {
        imageView.imageTintList = ColorStateList.valueOf(Color.parseColor(
            if(saving.endDate.isBlank()) when (saving.id) {
                1, 4 -> "#FFFFFF"
                else -> "#606060"
            } else "#606060"))
    }

    @JvmStatic
    @BindingAdapter("textForm_goal")
    fun setGoalText(textView: TextView, goal: Int) {
        textView.text = "목표 금액 : "+ConvertUtils.convertMoney(goal)+" 원"
    }

    @JvmStatic
    @BindingAdapter("textForm_money")
    fun setMoneyText(textView: TextView, money: Int) {
        textView.text = "저축 금액 : "+ConvertUtils.convertMoney(money)+" 원"
    }

    @JvmStatic
    @BindingAdapter("savingAdapter")
    fun addSavingManageAdapter(recyclerView: RecyclerView, savingViewModel: SavingViewModel) {
        recyclerView.adapter = SavingManageAdapter(savingViewModel)
    }

    @JvmStatic
    @BindingAdapter("visibility_cardButton")
    fun setVisibleButton(button: TextView, cardNumber: String) {
        button.visibility = if(cardNumber.isBlank()) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("visibility_isStart")
    fun setVisibleIsStarted(view: LinearLayout, isStarted:Boolean) {
        view.visibility = if(isStarted) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("onStartButtonClick")
    fun onStartButtonClick(button: Button, id: Int) {
        button.setOnClickListener {
            when(id) {
                1 -> button.context.startActivity(Intent())
            }
        }
    }

    @JvmStatic
    @BindingAdapter("timeLineText")
    fun setTimeLineText(textView: TextView, money: Int) {
        textView.text = ConvertUtils.convertMoney(money) + "원 저축"
    }

    @JvmStatic
    @BindingAdapter("timeLineStudyTime")
    fun setTimeLineStudyTime(textView: TextView, time: Int) {
        textView.text = ConvertUtils.convertHour(time)
    }

    @JvmStatic
    @BindingAdapter("timeLineAdapter")
    fun setTimeLineAdapter(recyclerView: RecyclerView, viewModel: SavingViewModel) {
        recyclerView.adapter = TimelineAdapter(viewModel)
    }

    @JvmStatic
    @BindingAdapter("studyTimeVisiblity")
    fun setTimeLineAdapter(textView: TextView, id: Int) {
        textView.visibility = when (id) {
            3 -> View.VISIBLE
            else -> View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter("savingName")
    fun setSavingName(textView: TextView, id: Int) {
        textView.text = when(id) {
            1 -> "건강 : 다이어트 저축"
            2 -> "바른 생활 : 얼리 버드 저축"
            3 -> "자기 관리 : 공부 하기 저축"
            else -> "365 : 데일리 저축"
        }
    }

    @JvmStatic
    @BindingAdapter("savingCardNotEmpty")
    fun setVisibleNotEmpty(constraintLayout: ConstraintLayout, startDate: String?) {
//        Log.e("[바인딩어댑터1]", "실행되는지 확인")
//        Log.e("[바인딩어댑터1]", startDate.toString())
        constraintLayout.visibility = if(startDate != null) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("savingCardEmpty")
    fun setVisibleEmpty(constraintLayout: ConstraintLayout, startDate: String?) {
//        Log.e("[바인딩어댑터2]", "실행되는지 확인")
//        Log.e("[바인딩어댑터2]", startDate.toString())
        constraintLayout.visibility = if(startDate == null) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("justAccountText")
    fun setSpan(textView: TextView, boolean: Boolean) {
        if(boolean) {
            textView.setTextColor(Color.parseColor("#606060"))
            val span = textView.text as Spannable
            span.setSpan(
                ForegroundColorSpan(Color.parseColor("#2557ff")),
                0,
                4,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }
    }

    @JvmStatic
    @BindingAdapter("convertMoney")
    fun convertMoney(textView: TextView, money: Int) {
        val dec = DecimalFormat("#,###")
        textView.text = dec.format(money).toString()
    }

    @JvmStatic
    @BindingAdapter("completeVisibility")
    fun setVisibleComplete(imageView: ImageView, endDate: String) {
        imageView.visibility = if(endDate.isNotBlank()) View.VISIBLE else View.GONE
    }


}