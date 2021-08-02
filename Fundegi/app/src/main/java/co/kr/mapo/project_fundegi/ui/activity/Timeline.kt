package co.kr.mapo.project_fundegi.ui.activity

data class Timeline (
    val pipeline : Int, //이미지
    val savingDate : String, //날짜(형식: 2021/01/01)
    val studyRecord: String, //공부시간(형식: 0시간 0분 공부)
    val savingAmount : String //저축금액(형식: 10,000원 저축)
)