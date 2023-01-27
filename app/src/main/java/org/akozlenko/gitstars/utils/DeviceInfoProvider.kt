package org.akozlenko.gitstars.utils

import java.util.*

class DeviceInfoProvider {

    fun getTimeZone(): TimeZone {
        return TimeZone.getDefault()
    }

    fun getCurrentTimeInMs(): Long {
        return System.currentTimeMillis()
    }

}
