package co.kr.mapo.project_fundegi.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-07-26
 * @desc
 */
class CustomEdgeTextView : AppCompatTextView {

    var mainColor: Int = Color.parseColor("#fd5f2f")     // 텍스트 컬러: 디폴트 흰색
    var edgeColor: Int = Color.BLACK     // 테두리 컬러: 디폴트 검은색
    var edgeWidth: Float = 18.0f          // 테두리 두깨: 디폴트 6

    /*
        생성자 -> 기본 생성자만 존재
    */

    constructor(ctx: Context):super(ctx)
    constructor(ctx: Context, attr: AttributeSet): super(ctx, attr)
    constructor(ctx: Context, attr: AttributeSet, def: Int) : super(ctx, attr, def)

    /*
        테두리를 그리게 만들어주는 메인 함수
        원리: 덮어씌우기
        테두리 텍스트를 좀 더 굵게 그리고, 그 위에 메인 텍스트 그리기
     */
    protected override fun onDraw(canvas: Canvas?) {
        // 테두리 그리기
        this.paint.style = Paint.Style.STROKE
        this.paint.strokeWidth = edgeWidth
        this.setTextColor(edgeColor)
        super.onDraw(canvas)

        // 메인 그리기
        this.paint.style = Paint.Style.FILL
        setTextColor(mainColor)
        super.onDraw(canvas)

    }

    /*
        색상과 두깨 변환하는 함수
        매개변수
            mainColor: 메인 컬러
            edgeColor: 테두리 컬러
            edgeWidth: 테두리 두깨
     */
    fun setTextEdgeAndColor(mainColor:Int, edgeColor:Int, edgeWidth:Float){
        this.mainColor = mainColor
        this.edgeColor = edgeColor
        this.edgeWidth = edgeWidth
    }

}