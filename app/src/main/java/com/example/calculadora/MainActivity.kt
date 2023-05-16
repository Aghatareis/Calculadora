package com.example.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadora.ui.theme.CalculadoraTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraTheme {
                    AppCalculadoraGorjeta()
                }
            }
        }
    }

@Preview
@Composable
fun AppCalculadoraGorjeta(){

    var valorentrada by remember {(mutableStateOf(""))}
    var gorjeta by remember {(mutableStateOf(0.0))}
    var percentagemGorjeta by remember {(mutableStateOf(""))}
    var arredondar by remember {(mutableStateOf(false))}

    val focusManager = LocalFocusManager.current

    gorjeta = CalcularGorjeta(valorentrada,percentagemGorjeta,arredondar)

    Column (
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            ) {
        Text(
            text = "Calculadora De Gorjeta",
            fontSize = 25.sp,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(30.dp)

        )
        CampoText(
            value =valorentrada ,
            label = "Valor De Entrada",
            onValueChange = {valorentrada = it},
            imeAction = ImeAction.Next,
            keyboardActions = KeyboardActions(
                onNext = {focusManager.moveFocus(androidx.compose.ui.focus.FocusDirection.Down)}
            )
        )

        Text(
            text = "Valor Da Gorjeta:${NumberFormat.getCurrencyInstance().format(gorjeta)}",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 30.dp)
        )

        CampoText(
            value = percentagemGorjeta,
            label = "% Gorjeta",
            onValueChange = {percentagemGorjeta = it},
            imeAction = ImeAction.Done,
            keyboardActions = KeyboardActions(
                onDone = {focusManager.clearFocus()}
            )

        )

        Row (
              verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(27.dp)
                ){
            Text(
                text = "Arredondar"
            )

            Switch(checked = arredondar,
                onCheckedChange = {arredondar = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.End)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoText (
    value:String,
    label: String,
    onValueChange:(String)->Unit,
    imeAction: ImeAction,
    keyboardActions: KeyboardActions
){
    TextField(
        value = value,
        label = {
            Text(
                text = label
            )

        },
        onValueChange = onValueChange,
        modifier = Modifier
            .padding(top=30.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,
        imeAction = imeAction),
        keyboardActions = keyboardActions
    )
}

fun CalcularGorjeta(
    valorentrada: String,
    percentagemGorjeta:String,
    arredondar:Boolean
):Double{

    var gorjeta = (valorentrada.toDoubleOrNull()?:0.0)* (percentagemGorjeta.toDoubleOrNull()?:0.0)/ 100

    if (arredondar==true){
        gorjeta = kotlin.math.ceil(gorjeta)
    }

    return gorjeta
}

