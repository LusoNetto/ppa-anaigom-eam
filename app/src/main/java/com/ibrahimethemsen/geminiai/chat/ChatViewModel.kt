package com.ibrahimethemsen.geminiai.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.ibrahimethemsen.geminiai.di.GeminiPro
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    @GeminiPro private val geminiPro : GenerativeModel
) : ViewModel() {
    private val _messageResponse = MutableLiveData<GenerateContentResponse>()
    val messageResponse : LiveData<GenerateContentResponse> get() = _messageResponse
    private val chat : Chat = geminiPro.startChat()

    val semanas = 3
    val comorbidades = "Anemia falsiforme e obesidade"
    val peso = "110 kg"
    val idade = 27
    val promptParaMenssagem = "" +
            "Este é apenas um prompt para uma resposta " +
            "apropriada para uma gravida de $semanas semanas" +
            ", com as seguintes comorbidades: $comorbidades, $idade anos de idade e" +
            " com um peso de $peso. " +
            "Ela não precisa saber que foi feito um prompt para dar uma" +
            " resposta apropriada, apenas responda a pegunta dela com base em " +
            "informações cientifica dando orientações e não deixe de sempre estar " +
            "sugerindo a orientação de um profissional de saúde, nunca deixe de sugerir," +
            "só não sugira se ela não perguntar nada." +
            " De uma resposta curta." +
            " Não cite suas comorbidades" +
            ", apenas se for realmente necessario. Se ela apresentar uma pergunta dizendo que" +
            "esta passando mau ou não esta tão bem, seja mais serio. responda em pt-br" +
            "Esta é a pergunta dela:\n"

    fun geminiChat(
        message : String
    ){
        viewModelScope.launch {
            _messageResponse.value = chat.sendMessage(promptParaMenssagem+message)
        }
    }
}