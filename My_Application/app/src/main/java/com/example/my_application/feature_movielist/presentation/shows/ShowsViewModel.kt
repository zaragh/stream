package com.example.my_application.feature_movielist.presentation.shows

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_application.feature_movielist.data.Resource
import com.example.my_application.feature_movielist.domain.usecase.GetShowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(private val showUseCase: GetShowUseCase) : ViewModel() {

    private val _status = mutableStateOf(ShowsListState())
    val status: State<ShowsListState> = _status

    private val _expandedCardIdsList = MutableStateFlow(listOf<String>())
    val expandedCardIdsList: StateFlow<List<String>> get() = _expandedCardIdsList

    init {
        getShows()
    }

    fun getShows() {
        showUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _status.value = ShowsListState(shows = result.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _status.value = ShowsListState(isLoading = true)
                }
                is Resource.Error -> {
                    _status.value = ShowsListState(error = result.message ?: "error occurred!")
                }
            }
        }.launchIn(viewModelScope)

    }

    fun onCardArrowClicked(cardId: String?) {
        _expandedCardIdsList.value = _expandedCardIdsList.value.toMutableList().also { list ->
            if (list.contains(cardId)) list.remove(cardId) else cardId?.let { list.add(it) }
        }
    }

}