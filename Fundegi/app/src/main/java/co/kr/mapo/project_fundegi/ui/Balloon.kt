package co.kr.mapo.project_fundegi.ui

import android.content.Context
import androidx.core.content.ContextCompat
import co.kr.mapo.project_fundegi.R
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.createBalloon

class Balloon(private val tip: String, context: Context) {
    val resisterTip = createBalloon(context) {
        setArrowSize(12) // 화살표 크기
        setArrowPosition(0.42f) // 화살표 위치(0 ~ 1.0)
        setWidth(BalloonSizeSpec.WRAP)
        setHeight(65)
        setCornerRadius(4f)
        setAlpha(0.9f)
        setText(tip)
        setMarginLeft(30)
        setMarginRight(30)
        setPadding(10)
        setTextColorResource(R.color.fundaegi_color2)
        setTextIsHtml(true)
        setBackgroundColorResource(R.color.fundaegi_color1)
        setBalloonAnimation(BalloonAnimation.FADE)
        setLifecycleOwner(lifecycleOwner)
        //setOnBalloonClickListener(onBalloonClickListener)
    }

    val mainTip = createBalloon(context) {
        setArrowSize(10) // 화살표 크기
        setArrowPosition(0.94f) // 화살표 위치(0 ~ 1.0)
        setWidth(BalloonSizeSpec.WRAP)
        setHeight(BalloonSizeSpec.WRAP)
        setCornerRadius(4f)
        setAlpha(0.9f)
        setText(tip)
        setMarginLeft(8)
        setMarginRight(20)
        setPadding(14)
        setTextColorResource(R.color.fundaegi_color2)
        setTextIsHtml(true)
        setBackgroundColorResource(R.color.fundeagi_first2)
        setBalloonAnimation(BalloonAnimation.FADE)
        setLifecycleOwner(lifecycleOwner)
        //setIconDrawable(ContextCompat.getDrawable(context, R.drawable.fundegi_diet))
        //setOnBalloonClickListener(onBalloonClickListener)
    }

}
