package com.kslim.presentation.ui.model

import com.kslim.domain.result.DataError

fun DataError.toUiMessage(): String {
    return when (this) {
        DataError.Network -> "인터넷 연결을 확인해주세요."
        DataError.Timeout -> "요청 시간이 초과되었습니다."
        DataError.Unauthorized -> "인증이 필요합니다."
        DataError.Forbidden -> "접근 권한이 없습니다."
        DataError.NotFound -> "데이터를 찾을 수 없습니다."
        DataError.Server -> "서버 오류가 발생했습니다."
        is DataError.Unknown -> this.message ?: "알 수 없는 오류"
    }
}