package tw.edu.pu.csim.s1131633.race

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    var screenWidthPx by mutableStateOf(0f)
        private set

    var screenHeightPx by mutableStateOf(0f)
        private set

    var gameRunning by mutableStateOf(false)
        private set

    // 三匹馬的位置 (注意:使用大寫 X)
    var horse1X by mutableStateOf(0f)
        private set

    var horse2X by mutableStateOf(0f)
        private set

    var horse3X by mutableStateOf(0f)
        private set

    // 三匹馬的動畫幀索引 (0-3)
    var horse1Frame by mutableStateOf(0)
        private set

    var horse2Frame by mutableStateOf(0)
        private set

    var horse3Frame by mutableStateOf(0)
        private set

    // 獲勝的馬 (0表示沒有, 1/2/3表示第幾匹馬獲勝)
    var winnerHorse by mutableStateOf(0)
        private set

    // 設定螢幕寬度與高度
    fun SetGameSize(w: Float, h: Float) {
        screenWidthPx = w
        screenHeightPx = h
        // 初始化三匹馬的位置
        horse1X = 0f
        horse2X = 0f
        horse3X = 0f
    }

    fun StartGame() {
        if (gameRunning) return // 避免重複啟動

        gameRunning = true
        winnerHorse = 0

        // 回到初始位置
        horse1X = 0f
        horse2X = 0f
        horse3X = 0f
        horse1Frame = 0
        horse2Frame = 0
        horse3Frame = 0

        viewModelScope.launch {
            while (gameRunning) {
                delay(100) // 每0.1秒循環

                // 三匹馬都往前移動(隨機速度)
                horse1X += (30..70).random()
                horse2X += (30..70).random()
                horse3X += (30..70).random()

                // 切換馬的動畫幀
                horse1Frame = (horse1Frame + 1) % 4
                horse2Frame = (horse2Frame + 1) % 4
                horse3Frame = (horse3Frame + 1) % 4

                // 檢查是否有馬到達終點
                if (horse1X >= screenWidthPx - 200f && winnerHorse == 0) {
                    winnerHorse = 1
                    gameRunning = false
                }
                if (horse2X >= screenWidthPx - 200f && winnerHorse == 0) {
                    winnerHorse = 2
                    gameRunning = false
                }
                if (horse3X >= screenWidthPx - 200f && winnerHorse == 0) {
                    winnerHorse = 3
                    gameRunning = false
                }
            }
        }
    }

    fun StopGame() {
        gameRunning = false
    }
}
