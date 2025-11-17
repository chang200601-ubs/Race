package tw.edu.pu.csim.s1131633.race

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameScreen(message: String, gameViewModel: GameViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8DC)) // 米黃色背景
    ) {
        // 遊戲畫布 - 繪製跑道
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            // 繪製跑道線條
            for (i in 1..3) {
                val y = canvasHeight * i / 4f
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, y),
                    end = Offset(canvasWidth, y),
                    strokeWidth = 3f
                )
            }
        }

        // 馬的圖片列表
        val horseImages = listOf(
            R.drawable.horse0,
            R.drawable.horse1,
            R.drawable.horse2,
            R.drawable.horse3
        )

        // 第一匹馬(上方跑道)
        Image(
            painter = painterResource(id = horseImages[gameViewModel.horse1Frame]),
            contentDescription = "Horse 1",
            modifier = Modifier
                .size(200.dp, 100.dp)
                .offset {
                    IntOffset(
                        gameViewModel.horse1X.toInt(),
                        (gameViewModel.screenHeightPx / 4f - 50f).toInt()
                    )
                }
        )

        // 第二匹馬(中間跑道)
        Image(
            painter = painterResource(id = horseImages[gameViewModel.horse2Frame]),
            contentDescription = "Horse 2",
            modifier = Modifier
                .size(200.dp, 100.dp)
                .offset {
                    IntOffset(
                        gameViewModel.horse2X.toInt(),
                        (gameViewModel.screenHeightPx / 2f - 50f).toInt()
                    )
                }
        )

        // 第三匹馬(下方跑道)
        Image(
            painter = painterResource(id = horseImages[gameViewModel.horse3Frame]),
            contentDescription = "Horse 3",
            modifier = Modifier
                .size(200.dp, 100.dp)
                .offset {
                    IntOffset(
                        gameViewModel.horse3X.toInt(),
                        (gameViewModel.screenHeightPx * 3f / 4f - 50f).toInt()
                    )
                }
        )

        // 上方資訊欄
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Text(
                text = "賽馬遊戲(作者:張伊傑)",
                color = Color.Black,
                fontSize = 20.sp,
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.7f))
                    .padding(8.dp)
            )

            // 顯示獲勝訊息
            if (gameViewModel.winnerHorse > 0) {
                Text(
                    text = "第${gameViewModel.winnerHorse}匹馬獲勝!",
                    color = Color.Red,
                    fontSize = 32.sp,
                    modifier = Modifier
                        .background(Color.Yellow.copy(alpha = 0.9f))
                        .padding(16.dp)
                )
            }
        }

        // 下方控制按鈕
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Button(
                onClick = { gameViewModel.StartGame() },
                enabled = !gameViewModel.gameRunning
            ) {
                Text("開始遊戲")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { gameViewModel.StopGame() },
                enabled = gameViewModel.gameRunning
            ) {
                Text("停止遊戲")
            }
        }
    }
}
