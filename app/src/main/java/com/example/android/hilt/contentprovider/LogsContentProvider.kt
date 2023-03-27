package com.example.android.hilt.contentprovider

import android.content.*
import android.database.Cursor
import android.net.Uri
import com.example.android.hilt.data.LogDao
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

/** The authority of this content provider.  */
private const val LOGS_TABLE = "logs"

/** The authority of this content provider.  */
private const val AUTHORITY = "com.example.android.hilt.provider"

/** The match code for some items in the Logs table.  */
private const val CODE_LOGS_DIR = 1

/** The match code for an item in the Logs table.  */
private const val CODE_LOGS_ITEM = 2

//ESTA CLASE NECESITA UNA INSTANCIA DEL DAO, QUE ES LA INTERFAZ DE CRUD PARA LA TABLA
//DE LA BD, SIN EMBARGO COMO ESTA CLASE HEREDA DE CONTENTPROVIDER HILT NO LA SOPORTA
//POR LO QUE SE DEBE INCLUIR EL OBJETO DAO DE OTRA FORMA
class LogsContentProvider : ContentProvider() {

    //EN ESTA CLASE AÑADE DE OTRA FORMA EL OBJETO
    //LOGDAO QUE  YA HILT SABE COMO PROPORCIONAR
    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface LogsContentProviderEntryPoint{
        fun logDao() : LogDao
    }

    //A TRAVES DE EntryPointAccessors ACCEDEMOS AL COMPONENTE
    private fun getLogDao(appContext: Context): LogDao {
        val hiltEntryPoint = EntryPointAccessors.fromApplication(
            appContext,
            LogsContentProviderEntryPoint::class.java
        )
        return hiltEntryPoint.logDao()
    }

    private val matcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITY, LOGS_TABLE, CODE_LOGS_DIR)
        addURI(AUTHORITY, "$LOGS_TABLE/*", CODE_LOGS_ITEM)
    }

    override fun onCreate(): Boolean = true

    override fun query(
        uri: Uri,
        proyection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {

        val code: Int = matcher.match(uri)
        return if (code == CODE_LOGS_DIR || code == CODE_LOGS_ITEM) {

            val appContext = context?.applicationContext ?: throw IllegalStateException()

            val logDao: LogDao = getLogDao(appContext)

            val cursor: Cursor? = if (code == CODE_LOGS_DIR) {
                logDao.selectAllLogsCursor()
            } else {
                logDao.selectLogById(ContentUris.parseId(uri))
            }

            cursor?.setNotificationUri(appContext.contentResolver, uri)
            cursor
        } else {
            throw IllegalArgumentException("Unknown URI: $uri")
        }

    }

    override fun getType(p0: Uri): String? {
        throw UnsupportedOperationException("Only reading operations are allowed")
    }

    override fun insert(
        p0: Uri,
        p1: ContentValues?
    ): Uri? {
        throw UnsupportedOperationException("Only reading operations are allowed")
    }

    override fun delete(
        p0: Uri,
        p1: String?,
        p2: Array<out String>?
    ): Int {
        throw UnsupportedOperationException("Only reading operations are allowed")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        throw UnsupportedOperationException("Only reading operations are allowed")
    }

}