package com.example.android.hilt.data

import dagger.hilt.android.scopes.ActivityScoped
import java.util.LinkedList
import javax.inject.Inject

@ActivityScoped
class LoggerInMemoryDataSource @Inject constructor () : LoggerDataSource {

    //SE USA LINKED LIST YA QUE ES MÁS EFICIENTE PARA
    //INSERTAR Y ELIMINAR QUE UNA LISTA NORMAL, ADEMÁS
    //COMO PARA EL PROGRAMA NO ES VITAL RECUPERAR ELEMENTOS EN PARTICCULAR
    //SE SUPLE LA DESVENTAJA DE ESTE TIPO DE LISTA
    private val logs: LinkedList<Log> = LinkedList()

    override fun addLog(msg: String) {
        logs.addFirst(
            Log(
                msg,
                System.currentTimeMillis()
            )
        )
    }

    override fun getAllLogs(callback: (List<Log>) -> Unit) {
        callback(logs)
    }

    override fun removeLogs() {
        logs.clear()
    }

}