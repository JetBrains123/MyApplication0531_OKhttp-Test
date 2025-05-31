package com.example.myapplication0531

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication0531.ui.theme.MyApplication0531Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplication0531Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 使用 viewModel() 函数获取 MainViewModel 实例
                    // Compose 会自动记住和管理 ViewModel 的生命周期
                    NetworkScreen(viewModel = viewModel())
                }
            }
        }
    }
}

@Composable
fun NetworkScreen(viewModel: Main2ViewModel) {
    val currentNetworkState = viewModel.networkState // 这是一个 val，其值在这一刻被确定
    val scope = rememberCoroutineScope()
    // 定义字符状态
    val textState = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 根据 networkState 的不同状态来显示不同的 UI
        when (currentNetworkState) {
            NetworkState.Idle -> {
                Text(
                    text = "点击按钮开始请求数据",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            NetworkState.Loading -> {
                Text(
                    text = "正在请求数据...",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator() // 加载指示器
            }

            is NetworkState.Success -> {
                Text(
                    text = currentNetworkState.data, // 显示成功数据
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            is NetworkState.Error -> {
                Text(
                    text = "错误: ${currentNetworkState.message}", // 显示错误信息
                    color = MaterialTheme.colorScheme.error, // 错误文本颜色
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Text(textState.value)
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                 viewModel.fetchData()


            }
        ) {
            Text(text = "请求数据")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NetworkScreenPreview() {
    MyApplication0531Theme {
        NetworkScreen(viewModel = viewModel())
    }

}

