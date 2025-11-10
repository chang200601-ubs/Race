package tw.edu.pu.csim.s1131633.race

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel: ViewModel() {

    var screenWidthPx by mutableStateOf(0f)
        private set

    var screenHeightPx by mutableStateOf(0f)
        private set

    var gameRunning by mutableStateOf(true)

    var circleX by mutableStateOf(0f)
    var circleY by mutableStateOf( 0f)

    var score by mutableStateOf(0)

    // 設定螢幕寬度與高度
    fun SetGameSize(w: Float, h: Float) {
        screenWidthPx = w
        screenHeightPx = h
        // 初始化圓形位置
        circleX = 100f
        circleY = h - 100f
    }

    fun MoveCircle(x: Float, y: Float) {
        circleX += x
        circleY += y

        // 限制圓形不超出螢幕
        if (circleX < 100f) circleX = 100f
        if (circleX > screenWidthPx - 100f) circleX = screenWidthPx - 100f
        if (circleY < 100f) circleY = 100f
        if (circleY > screenHeightPx - 100f) circleY = screenHeightPx - 100f
    }

    fun StartGame() {
        if (gameRunning) return // 避免重複啟動

        gameRunning = true
        score = 0

        // 回到初始位置
        circleX = 100f
        circleY = screenHeightPx - 100f

        viewModelScope.launch {
            while (gameRunning) {
                delay(100) // 每0.1秒循環
                circleX += 10

                if (circleX >= screenWidthPx - 100f) {
                    circleX = 100f
                    score++
                }
            }
        }
    }

    fun StopGame() {
        gameRunning = false
    }
}