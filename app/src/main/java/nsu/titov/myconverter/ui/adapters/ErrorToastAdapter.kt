package nsu.titov.myconverter.ui.adapters

import android.content.Context
import android.widget.Toast
import nsu.titov.myconverter.R
import nsu.titov.myconverter.domain.models.ErrorType

class ErrorToastAdapter {
    fun onError(error: ErrorType, context: Context) {
        val str = when (error) {
            ErrorType.DATABASE_ERROR -> context.getString(R.string.database_error)
            ErrorType.NETWORK_ERROR -> context.getString(R.string.network_error)
            else -> return
        }
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
    }
}