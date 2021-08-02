package co.kr.mapo.project_fundegi.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.kr.mapo.project_fundegi.system.AppPrefs

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-07-07
 * @desc 핀번호 뷰모델
 */
class PinNumberViewModel : ViewModel() {

    val pin_num1 = MutableLiveData<String?>()
    val pin_num2 = MutableLiveData<String?>()
    val pin_num3 = MutableLiveData<String?>()
    val pin_num4 = MutableLiveData<String?>()
    val pin_num5 = MutableLiveData<String?>()
    val pin_num6 = MutableLiveData<String?>()
    val pinNumber = MutableLiveData<String?>()
    val numbers = MutableLiveData<List<String>>()
    val failCount = MutableLiveData<Int>()
    private var inputCount = 0

    init {
        numbers.value = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "")
        failCount.value = 0
        pinNumber.value = ""
    }

    fun inputPin(num: String) {
        if(inputCount < 6) {
            if (num != "") {
                when (inputCount) {
                    0 -> pin_num1.value = num
                    1 -> pin_num2.value = num
                    2 -> pin_num3.value = num
                    3 -> pin_num4.value = num
                    4 -> pin_num5.value = num
                    5 -> pin_num6.value = num
                }
                pinNumber.value += num
                inputCount++
            }
        }
    }

    fun removePin() {
        if(inputCount > 0) {
            when (inputCount) {
                1 -> pin_num1.value = null
                2 -> pin_num2.value = null
                3 -> pin_num3.value = null
                4 -> pin_num4.value = null
                5 -> pin_num5.value = null
                6 -> pin_num6.value = null
            }
            if(pinNumber.value!!.isNotBlank()) {
                pinNumber.value = pinNumber.value!!.substring(0, pinNumber.value!!.length - 1)
            }
            inputCount--
        }
    }

    fun getPin() = pin_num1.value+pin_num2.value+pin_num3.value+pin_num4.value+pin_num5.value+pin_num6.value

    fun shuffleNumber() {
        numbers.value = numbers.value!!.shuffled()
    }

    fun resetPin() {
        pin_num1.value = null
        pin_num2.value = null
        pin_num3.value = null
        pin_num4.value = null
        pin_num5.value = null
        pin_num6.value = null
        pinNumber.value = ""
        inputCount = 0
    }

    fun increaseFailCount() {
        failCount.value = failCount.value!! +1
    }

    fun clearFailCount() {
        failCount.value = 0
    }

    public override fun onCleared() {
        super.onCleared()
    }

}