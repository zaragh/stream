package com.example.my_application.feature_movielist.domain.usecase

import com.example.my_application.feature_movielist.data.Resource
import com.example.my_application.feature_movielist.data.dto.toShow
import com.example.my_application.feature_movielist.domain.model.Show
import com.example.my_application.feature_movielist.domain.repository.ShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetShowUseCase @Inject constructor(private val repository: ShowRepository) {

    operator fun invoke(): Flow<Resource<List<Show>>> = flow {
        try {
            emit(Resource.Loading<List<Show>>())
            val shows = repository.getShows().map { it.toShow() }
            emit(Resource.Success<List<Show>>(shows))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Show>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Show>>("Couldn't reach server. Check your internet connection."))
        }
    }
}