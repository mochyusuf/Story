package com.mocyusuf.story.Utils

import android.content.Context
import android.widget.Toast

object Message {
    fun setMessage(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}