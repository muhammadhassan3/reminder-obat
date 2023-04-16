package com.muhammhassan.reminderobat.ui.view.progress

import androidx.lifecycle.ViewModel
import com.muhammhassan.reminderobat.domain.usecase.ProgressUseCase

class ProgressViewModel(useCase: ProgressUseCase): ViewModel() {
    val data = useCase.getData()
}