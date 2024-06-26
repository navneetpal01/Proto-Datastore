package com.example.proto_datastore

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.dataStore
import com.example.proto_datastore.ui.theme.ProtoDatastoreTheme
import kotlinx.collections.immutable.mutate
import kotlinx.coroutines.launch


// So in datastore whenever the preferences changes our states are automatically updated and keeps our UI up-to-date

val Context.dataStore by dataStore("app-settings.json", AppSettingsSerializer)

val appSettings = AppSettings().toString()

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )
        super.onCreate(savedInstanceState)
        setContent {
            ProtoDatastoreTheme {
                val appSettings = dataStore.data.collectAsState(
                    initial = AppSettings()
                ).value
                val scope = rememberCoroutineScope()
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    for (i in 0..2) {
                        val language = Language.values()[i]
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = language == appSettings.language,
                                onClick = {
                                    scope.launch {
                                        setLanguage(language = language)
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = language.toString())
                        }
                    }
                }
            }
        }

    }

    private suspend fun setLanguage(language: Language) {
        dataStore.updateData {
            it.copy(
                language = language,
                knownLocations = it.knownLocations.mutate {
                    it.add(
                        Location(
                            15.0,
                            15.0
                        )
                    )
                }
            )
        }
    }
}