package com.example.validation

import spock.lang.Specification

/**
 * Created by naga on 2014/02/23.
 */
class DateConstraintSpec extends Specification {

    def "日付文字列がDateFormatにて定義されていた形式に一致した場合、該当するEnum値を返却する"() {
        expect:
        DateConstraint.AllowedDateFormat.apply(date) == format

        where:
        date         | format
        null         | DateConstraint.AllowedDateFormat.UNDIFINED
        '20140101'   | DateConstraint.AllowedDateFormat.YYYYMMDDD
        '201401012'  | DateConstraint.AllowedDateFormat.UNDIFINED
        '2014/01/01' | DateConstraint.AllowedDateFormat.YYYYMMDD_DIVIDED_SLASH
        '2014/1/1'   | DateConstraint.AllowedDateFormat.YYYYMD_DIVIDED_SLASH
        '2014/1/123' | DateConstraint.AllowedDateFormat.UNDIFINED
        '2014//'     | DateConstraint.AllowedDateFormat.UNDIFINED
        '2014/99/99' | DateConstraint.AllowedDateFormat.YYYYMMDD_DIVIDED_SLASH
    }
}
