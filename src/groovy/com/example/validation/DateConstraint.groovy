package com.example.validation

import org.codehaus.groovy.grails.validation.AbstractConstraint
import org.codehaus.groovy.grails.validation.ConstrainedProperty
import org.springframework.validation.Errors

import java.util.regex.Pattern

/**
 * 日付文字列の正当性をチェックする制約です。
 * 以下のいずれかのフォーマット、かつ有効な日付であることを検査します。
 * <ul>
 *     <li>yyyyMMdd</li>
 *     <li>yyyy/MM/dd</li>
 *     <li>yyyy/M/d</li>
 * </ul>
 *
 * @author naga
 */
public class DateConstraint extends AbstractConstraint {

    static final String DATE_CONSTRAINT = "date";
    static final String DEFAULT_INVALID_DATE_MESSAGE_CODE = "default.invalid.date.message";
    static final String DEFAULT_INVALID_DATE_FORMAT_MESSAGE_CODE = "default.invalid.date.format.message";
    private static final String INVALID_SUFFIX = ".invalid"

    static enum AllowedDateFormat {

        YYYYMMDDD('yyyyMMdd', ~'^\\d{8}$'),
        YYYYMMDD_DIVIDED_SLASH('yyyy/MM/dd', ~'^\\d{4}/\\d{2}/\\d{2}$'),
        YYYYMD_DIVIDED_SLASH('yyyy/M/d', ~'^\\d{4}/\\d{1,2}/\\d{1,2}$'),
        UNDIFINED('', ~'')

        private String format
        private Pattern pattern

        AllowedDateFormat(String format, Pattern pattern) {
            this.format = format
            this.pattern = pattern
        }

        def getFormat() { return format }

        /**
         * 日付文字列に該当するフォーマットを返却します.
         * @param date nullable
         * @return 引数が@{ccode null}、または許可されていない形式の場合 @{code UNDIFINED} を返却します。
         */
        static AllowedDateFormat apply(String date) {

            if (!date) { return UNDIFINED }

            for (AllowedDateFormat adf : values()) {
                if (adf.pattern.matcher(date))
                    return adf
            }

            return UNDIFINED
        }
    }

    private boolean date;

    /* (non-Javadoc)
     * @see org.codehaus.groovy.grails.validation.ConstrainedProperty.AbstractConstraint#setParameter(java.lang.Object)
     */
    @Override
    public void setParameter(Object constraintParameter) {
        if (!(constraintParameter instanceof Boolean)) {
            throw new IllegalArgumentException(
                    "Parameter for constraint [${DATE_CONSTRAINT}] of property "
                    + "[${constraintPropertyName}] of class [${constraintOwningClass}] "
                    + "must be a boolean value")
        }

        date = (Boolean)constraintParameter
        super.setParameter(constraintParameter)
    }

    public String getName() {
        return DATE_CONSTRAINT;
    }

    /* (non-Javadoc)
     * @see org.codehaus.groovy.grails.validation.Constraint#supports(java.lang.Class)
     */
    @SuppressWarnings("rawtypes")
    public boolean supports(Class type) {
        return type != null && String.class.isAssignableFrom(type)
    }

    @Override
    protected boolean skipBlankValues() {
        true
    }

    @Override
    protected void processValidate(Object target, Object propertyValue, Errors errors) {

        if (!date) { return }

        String org = (String)propertyValue
        AllowedDateFormat format = AllowedDateFormat.apply(org)

        if (format == AllowedDateFormat.UNDIFINED) {
            // 指定外の形式の場合
            Object[] args = [constraintPropertyName, constraintOwningClass, propertyValue]
            rejectValue(target, errors, DEFAULT_INVALID_DATE_FORMAT_MESSAGE_CODE,
                    DATE_CONSTRAINT + INVALID_SUFFIX, args)
            return
        }

        def parsed = Date.parse(format.format, org).format(format.format)
        if(org != parsed) {
            // 日付として不正
            Object[] args = [constraintPropertyName, constraintOwningClass, propertyValue]
            rejectValue(target, errors, DEFAULT_INVALID_DATE_MESSAGE_CODE,
                    DATE_CONSTRAINT + INVALID_SUFFIX, args)
        }
    }
}
