package com.example.android.hilt.di

import com.example.android.hilt.data.LoggerDataSource
import com.example.android.hilt.data.LoggerInMemoryDataSource
import com.example.android.hilt.data.LoggerLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

//PODEMOS NOMBRAR UN ARCHIVO CON UN NOMBRE DIFERENTE AL
//DE LAS CLASES CON EL FIN DE AGRUPAR FUNCIONALIDAD POR
//EJEMPLO ESTE CASO, EN EL CUAL AGRUPAMOS LA FUNCIONALIDAD DE
//GUARDAR EN LA MEMORIA O EN ROOM

//SE CREAN DOS CALIFICADORES PARA CADA TIPO DE IMPLEMENTACIÓN DEVUELTA
//COMO UN LOGGERDATASOURCE
@Qualifier
annotation class InMemoryLogger

@Qualifier
annotation class DatabaseLogger



//SE HACE DE ESTA FORMA PORQUE LOS ALCANCES DE COMPONENTE SON DISTINTOS
//ENTONCES CREAMOS DOS MÓDULOS

@Module
@InstallIn(SingletonComponent::class)
abstract class LoggingDatabaseModule {

    @DatabaseLogger
    @Singleton
    @Binds
    abstract fun bindDatabaseLogger(
        impl: LoggerLocalDataSource
    ): LoggerDataSource

}


@InstallIn(ActivityComponent::class)
@Module
abstract class LoggingInMemoryModule {

    @InMemoryLogger
    @ActivityScoped
    @Binds
    abstract fun bindInMemoryLogger(
        impl: LoggerInMemoryDataSource
    ): LoggerDataSource

}

